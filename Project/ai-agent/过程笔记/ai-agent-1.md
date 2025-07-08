# ai-agent-1-preheat解析

接着上篇对于Controller的解析, 我们现在需要深入到service-controller中的domain层的具体实现, 来看详细看业务的实现

按照实际业务流程-preheat -> ragupload -> chat -> task的顺序解析

这篇主要解析preheat方法(也是实现最复杂的一个)

## preheat解析

- AiAgentPreheatService中的preheat

```java
public void preheat(Long aiClientId) throws Exception {
    StrategyHandler<AiAgentEngineStarterEntity, DefaultArmoryStrategyFactory.DynamicContext, String> handler = defaultArmoryStrategyFactory.strategyHandler();
    handler.apply(AiAgentEngineStarterEntity.builder()
                  .clientIdList(Collections.singletonList(aiClientId))
                  .build(), new DefaultArmoryStrategyFactory.DynamicContext());
}
```

可以看到这里这个方法主要就是从规则树工厂defaultArmoryStrategyFactory中获取到根节点, 然后执行整颗树(这部分的具体实现是在扳手工程中, 可以看我在扳手工程下写的作业)

> 这里简单说明下这个规则树框架怎么使用, 类继承AbstractMultiThreadStrategyRouter<T, D, R>, 每个树节点通过重写get方法的返回来控制下一个要访问的节点, 重写doapply()方法来处理这个节点要处理的业务流程, 重写multiThread()来异步加载数据

我们可以大致知道preheat有一个预热的流程, 按着树逐步完成预热, 顺着树读流程就能知道preheat整个流程

### 规则树的工厂

```java
public class DefaultArmoryStrategyFactory {
    @Resource
    private ApplicationContext applicationContext;
    private final RootNode rootNode;

    public StrategyHandler<AiAgentEngineStarterEntity, DefaultArmoryStrategyFactory.DynamicContext, String> strategyHandler() {
        return rootNode;
    }

    public ChatClient chatClient(Long clientId) {
        return (ChatClient) applicationContext.getBean("ChatClient_" + clientId);
    }

    public ChatModel chatModel(Long modelId) {
        return (ChatModel) applicationContext.getBean("AiClientModel_" + modelId);
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {

        private int level;

        private Map<String, Object> dataObjects = new HashMap<>();

        public <T> void setValue(String key, T value) {
            dataObjects.put(key, value);
        }

        public <T> T getValue(String key) {
            return (T) dataObjects.get(key);
        }
    }
}
```

上面的strategyHandler()方法向外暴露了规则树的根节点
chatClient()与chatModel()方法从applicationContext中获取Bean说明在别的业务代码中我们会运行时创建bean

DynamicContext中level记录了现在在树中的深度, dataObjects就是运行时上下文的记录

### AbstractArmorySupport



RootNode继承自AbstractArmorySupport我们还得先从这个类开始看

```java
public abstract class AbstractArmorySupport extends AbstractMultiThreadStrategyRouter<AiAgentEngineStarterEntity, DefaultArmoryStrategyFactory.DynamicContext, String> {

    private final Logger log = LoggerFactory.getLogger(AbstractArmorySupport.class);
    @Resource
    protected ApplicationContext applicationContext;
    @Resource
    protected ThreadPoolExecutor threadPoolExecutor;
    @Resource
    protected IAgentRepository repository;
    /**
     * 通用的Bean注册方法
     *
     * @param beanName  Bean名称
     * @param beanClass Bean类型
     * @param <T>       Bean类型
     */
    protected synchronized <T> void registerBean(String beanName, Class<T> beanClass, T beanInstance) {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();

        // 注册Bean
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass, () -> beanInstance);
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
        beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);

        // 如果Bean已存在，先移除
        if (beanFactory.containsBeanDefinition(beanName)) {
            beanFactory.removeBeanDefinition(beanName);
        }

        // 注册新的Bean
        beanFactory.registerBeanDefinition(beanName, beanDefinition);

        log.info("成功注册Bean: {}", beanName);
    }

    protected <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

}
```

去掉这个类中占位的方法, 剩下就是registerBean这个核心方法和getBean这个获取已经注册好的bean的方法(这里能自动转换类型)

#### registerBean方法

通用的注册一个bean的方法, 传入beanName, 说明bean类型的beanClass, bean实例的beanInstance

1. 先获取Spring的核心Bean工厂实现类(用于管理Bean的生命周期, 提供注册bean和移除的API), 强转是因为applicationContext返回的是接口, 转换成DefaultListableBeanFactory来使用高级功能
2. 构建Bean的定义, 需要传入beanClass和instance. 其中的`() -> beanInstance`: Lambda表达式作为Bean实例供应器, 设置Bean的作用域, 这里设置为单例模式
3. 如果存在, 删除, 通过bean工厂实现类来注册新的bean

> 这个类主要就是为了额外提供一个动态注册bean的方法

### RootNode

```java
public class RootNode extends AbstractArmorySupport {

    @Resource
    private AiClientToolMcpNode aiClientToolMcpNode;

    @Override
    protected void multiThread(AiAgentEngineStarterEntity requestParameter, DefaultArmoryStrategyFactory.DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<List<AiClientModelVO>> aiClientModelListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_model) {}", requestParameter.getClientIdList());
            return repository.queryAiClientModelVOListByClientIds(requestParameter.getClientIdList());
        }, threadPoolExecutor);

        CompletableFuture<List<AiClientToolMcpVO>> aiClientToolMcpListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_tool_mcp) {}", requestParameter.getClientIdList());
            return repository.queryAiClientToolMcpVOListByClientIds(requestParameter.getClientIdList());
        }, threadPoolExecutor);

        CompletableFuture<List<AiClientAdvisorVO>> aiClientAdvisorListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_advisor) {}", requestParameter.getClientIdList());
            return repository.queryAdvisorConfigByClientIds(requestParameter.getClientIdList());
        }, threadPoolExecutor);

        CompletableFuture<Map<Long, AiClientSystemPromptVO>> aiSystemPromptConfigFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client_system_prompt) {}", requestParameter.getClientIdList());
            return repository.querySystemPromptConfigByClientIds(requestParameter.getClientIdList());
        }, threadPoolExecutor);

        CompletableFuture<List<AiClientVO>> aiClientListFuture = CompletableFuture.supplyAsync(() -> {
            log.info("查询配置数据(ai_client) {}", requestParameter.getClientIdList());
            return repository.queryAiClientByClientIds(requestParameter.getClientIdList());
        }, threadPoolExecutor);

        CompletableFuture.allOf(aiClientModelListFuture)
                .thenRun(() -> {
                    dynamicContext.setValue("aiClientModelList", aiClientModelListFuture.join());
                    dynamicContext.setValue("aiClientToolMcpList", aiClientToolMcpListFuture.join());
                    dynamicContext.setValue("aiClientAdvisorList", aiClientAdvisorListFuture.join());
                    dynamicContext.setValue("aiSystemPromptConfig", aiSystemPromptConfigFuture.join());
                    dynamicContext.setValue("aiClientList", aiClientListFuture.join());
                }).join();
    }

    @Override
    protected String doApply(...) throws Exception {
		// 空
    }

    @Override
    public StrategyHandler<...> get(...) {
        return aiClientToolMcpNode; // 下一个要访问的节点
    }
}
```

doApply实际上就是空内容, 这个节点没有需要处理业务的内容

get说明RootNode的下一个Node是aiClientToolMcpNode

#### multiThread方法

从大的框架上了来说就是创建了一批要查询的任务, 然后异步多线程同时执行查询, 并在执行完查询以后, 将值存入dynamicContext中, 其中每个respository中的查询方法, 其实可以简单看作就是填充好VO对象的一个过程, 调用多个dao层接口, 组装数据库中的数据成实际的业务中会使用的VO对象

这些repository属于没什么复杂逻辑但是又是很重要的代码, 可以看看最后组装出来的各个VO对象具体是怎么样的, 以及是怎么组装出来, 这样的也能反向理解库表应该怎么配置, 建议通读一遍

> 为什么需要转化成VO对象? 某些地方能直接使用po对象但是还是多此一举的创建了一个几乎一模一样的VO对象?
>
> VO和Entity对象是业务中实际上使用的对象, 也是实际上在各个方法之间传递, 是"业务实体", 而持久化对象是面向库表的, 是我们从库表中读取出来的结构, 往往不会是和我们要的业务实体是一样的形式, 因为库表之间往往会进行解耦比如通过范式约束设计, 最后呈现出来的形式并不是和业务直接对应的
>
> 创建一个一样的VO对象是因为我们这里的业务实体刚好和PO对象相差无几, 一方面是为了保持编码风格的统一, 另一方面是为了保证以后的拓展性, 如果这里的业务发生了什么变化, 我们修改VO对象就行, 总不能修改PO对象吧
>
> VO（及其所在的聚合根Entity）是承载核心业务规则和逻辑的地方。直接使用PO会导致业务规则无法内聚在合适的对象中，散落在服务层或更糟的地方，违反高内聚原则。
>
> 同时使用VO对象还能封装简单的领域行为

### AiClientToolMcpNode

doApply()

```java
protected String doApply(...) throws Exception {
    log.info("Ai Agent 构建，tool mcp 节点 {}", JSON.toJSONString(requestParameter));
    List<AiClientToolMcpVO> aiClientToolMcpList = dynamicContext.getValue("aiClientToolMcpList");
    if (aiClientToolMcpList == null || aiClientToolMcpList.isEmpty()) {
        //"没有可用的AI客户端工具配置 MCP"
        return router(requestParameter, dynamicContext);
    }

    for (AiClientToolMcpVO mcpVO : aiClientToolMcpList) {
        // 创建McpSyncClient对象
        McpSyncClient mcpSyncClient = createMcpSyncClient(mcpVO);
        // 使用父类的通用注册方法
        registerBean(beanName(mcpVO.getId()), McpSyncClient.class, mcpSyncClient);
    }
    return router(requestParameter, dynamicContext);
}
```

整个程序的执行流程是从上下文中获取到aiClientToolMcpList也就是List\<AiClientToolMcpVO>, 如果获取到的不为空, 则将他们都注册成bean对象

#### createMcpSyncClient

这个方法的名称很明显地说明了这个方法地作用, 将我们的AiClientToolMcpVO注册成一个McpSyncClient

McpSyncClient是一个**同步**的MCP客户端接口, 用于与MCP服务器进行通信. MCP是一个标准协议, 允许AI模型访问外部工具和数据源

```java
// 与MCP服务器建立连接
var initResult = mcpSyncClient.initialize();

// 获取可用工具列表
var tools = mcpSyncClient.getTools();
// 调用特定工具
var result = mcpSyncClient.callTool(toolName, arguments);

// 获取资源列表
var resources = mcpSyncClient.getResources();
// 读取特定资源
var content = mcpSyncClient.readResource(resourceUri);
```



如果MCP工具是SSE传输模式

1. 配置解析
2. 解析URL: 如果URL里面有"sse", 将URL中的"sse"分割出去, 并且这种配置说明baseUri和sseEndpoint是在一起配置的, 否则使用分离配置 `"baseUri":"http://127.0.0.1:9999/sse?apikey=xxx"` ``"baseUri":"https://mcp.amap.com", "sseEndpoint":"/sse?key=xxx"`
3. 创建SSE客户端

```java
HttpClientSseClientTransport sseClientTransport = HttpClientSseClientTransport
    .builder(baseUri)
    .sseEndpoint(sseEndpoint)
    .build();

McpSyncClient mcpSyncClient = McpClient.sync(sseClientTransport)
    .requestTimeout(Duration.ofMinutes(aiClientToolMcpVO.getRequestTimeout()))
    .build();
```

4. 客户端初始化

```java
var init_sse = mcpSyncClient.initialize();
log.info("Tool SSE MCP Initialized {}", init_sse);
```

如果MCP工具是STDIO传输模式

1. 配置获取
2. 服务器参数构建(Command和args)
3. 创建客户端

### AiClientAdvisorNode

和上一个Node是一样的逻辑, 从Context中获取, 如果成功获取, 则将其注册为Advisor类型的Bean

#### createAdvisor

如果是ChatMemory聊天上下文类型

1. 从VO对象中获取聊天最长上下文长度
2. 构建并返回提示词记忆顾问

如果是RagAnswer知识库类型

1. 从VO对象中获取RagAnswer对象(topk, filter)
2. 传入向量数据库和用RagAnswer构建的SearchRequest来构建出来知识库顾问

#### PromptChatMemoryAdvisor

Spring AI实现的提示词记忆顾问

- 自动维护历史对话记录
- 在每次请求的时候将相关的历史消息自动注入到提示词中

```java
// 项目中的实际使用
ChatClient chatClient = ChatClient.builder(chatModel)
    .defaultAdvisors(
        // 添加记忆顾问
        new PromptChatMemoryAdvisor(MessageWindowChatMemory.builder()
            .maxMessages(10)  // 最多记住10条消息
            .build())
    )
    .build();
// 调用时指定会话ID
String response = chatClient
    .prompt("你好，我是张三")
    .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, "user_123"))
    .call()
    .content();
```

MessageWindowChatMemory是消息窗口记忆的实现, 通过滑动窗口的形式保存maxMessages条消息

#### RagAnswerAdvisor 

自定义的RAG顾问实现

这个方法是核心的从向量数据库中获取到和提问相关的documents, 然后由此注入上下文参数, 构建一个新的增强请求
        

    HashMap<String, Object> context = new HashMap(request.adviseContext());
    // 1. 构建增强提示词
    String advisedUserText = request.userText() + System.lineSeparator() + this.userTextAdvise;
    
    // 2. 渲染查询模板
    String query = new PromptTemplate(request.userText(), request.userParams()).render();
    
    // 3. 执行向量检索
    SearchRequest searchRequestToUse = SearchRequest.from(this.searchRequest)
    .query(query)
    .filterExpression(this.doGetFilterExpression(context))
    .build();
    List<Document> documents = this.vectorStore.similaritySearch(searchRequestToUse);
    
    // 4. 构建文档上下文
    context.put("qa_retrieved_documents", documents);
    String documentContext = (String)documents.stream()
    .map(Document::getText)
    .collect(Collectors.joining(System.lineSeparator()));
    Map<String, Object> advisedUserParams = new HashMap(request.userParams());
    
    // 5. 注入上下文参数
    advisedUserParams.put("question_answer_context", documentContext);
    
    AdvisedRequest advisedRequest = AdvisedRequest
    .from(request)
    .userText(advisedUserText)
    .userParams(advisedUserParams)
    .adviseContext(context).build();
    return advisedRequest;
    }

AdvisedRequest 和 AdvisedResponse 是 Spring AI 的增强请求和响应对象
这两个类的作用是在于提供一个增强的上下文，使得在处理请求和响应时可以携带额外的信息，比如用户的查询、上下文信息等。
通过增强请求和响应，可以在处理过程中注入更多的上下文信息
在before中处理请求时，可以根据用户的查询和上下文信息来构建一个更丰富的请求对象。
这里的上下文是通过知识库向量存储（VectorStore）进行检索得到的，增强请求可以包含检索到的相关文档信息。
这里的nextAroundCall和nextAroundStream方法是调用链的一部分，用于在增强请求处理完成后继续执行下一个环节的逻辑。
在aroundCall和aroundStream方法中，增强请求会被传递到下一个环节进行处理。
在处理响应时，增强响应对象可以携带更多的信息，比如检索到的文档信息、处理结果等。
也就是我能从增强相应对象中获取到rag知识库检索到的相关文档信息。

### AiClientModelNode

同上的流程, 不过创建的bean变成了OpenAiChatModel, 并且将所有这个模型可以使用的MCP Tool添加到模型中

### AiClientNode

在doApply方法中将chatModel, MCP, Advisor这三类Bean组装成一个对话客户端ChatClient, 然后将其注册为bean



## 总结

自此我们就完成了对于整个服务的预热, 将model, mcp, advisor注册成bean并相互绑定在一起, 这里使用规则树单纯是为了制定一个预热的流程

我们先根据aiClientIds从RootNode节点出发, 异步从库表中查询组装出来业务中流转的VO对象,  并将它们添加到Map中

然后进入到AiClientToolMcpNode, 根据Map中的MCPVOList组转起来所有McpSyncClient然后将其注册为Bean

进入到AiClientAdvisorNode, 根据Advisor的类型, 组装rag或者chatmemory类型的bean

再到AiClientModelNode, 组装OpenAiChatModel并这个model绑定的MCP Tool添加进去

最后就是AiClientNode将chatModel, MCP, Advisor这三类Bean组装成一个对话客户端ChatClient, 然后将其注册为bean
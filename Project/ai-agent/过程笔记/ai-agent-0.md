# ai-agent-0

自上而下的阅读, 优先理解整个业务框架, 可以看作是对于业务建模的逆推

## Controller

文件夹位置: trigger-http

比起细读每个controller的实现细节, 这部分在最开始阅读的时候, 我更倾向于关注更宏观的controller实现的功能, 避免在最开始的时候迷失在细节里面, 因为很多要想理解很多细节往往依托于对于整个业务框架的理解

通过大致理解每个controller的功能, 初步搭建起来整个ai-agent的业务

这步需要删繁就简, 删去和业务不直接相关的部分

### Service-Controller

业务核心相关的Controller部分, 这部分同样是不纠结于具体的实现, 我们只关注实现的业务是怎么样的, 这里只有一个Controller(其实在api层里的service就能看出来, 那里也只有一个service接口), 同时不同于CRUD相关的Controller我们是对持久化对象删繁就简, 这里我们是对业务删繁就简, 只留下来核心的处理来串联业务

#### preheat

```java
public Response<Boolean> preheat(@RequestParam("aiAgentId") Long aiClientId) {
    log.info("预热装配 AiAgent {}", aiClientId);
    aiAgentPreheatService.preheat(aiClientId);
}
```

根据aiClientId预热指定client的配置, 如果想进一步查看预热的内容, 我们就需要去查看preheat的具体实现

#### chatAgent

```java
 public Response<String> chatAgent(@RequestParam("aiAgentId") Long aiAgentId, @RequestParam("message") String message) {
     log.info("AiAgent 智能体对话，请求 {} {}", aiAgentId, message);
     String content = aiAgentChatService.aiAgentChat(aiAgentId, message);
}
```

指定AgentId进行对话

#### chatStream

```java
public Flux<ChatResponse> chatStream(@RequestParam("aiAgentId") Long aiAgentId, @RequestParam("ragId") Long ragId, @RequestParam("message") String message) {
        log.info("AiAgent 智能体对话(stream)，请求 {} {} {}", aiAgentId, ragId, message);
        return aiAgentChatService.aiAgentChatStream(aiAgentId, ragId, message);
}
```

指定aiagent并携带rag进行流式对话

#### uploadRagFile

```java
public Response<Boolean> uploadRagFile(@RequestParam("name") String name, @RequestParam("tag") String tag, @RequestParam("files") List<MultipartFile> files) {
        log.info("上传知识库，请求 {}", name);
        aiAgentRagService.storeRagFile(name, tag, files);
}
```

上传知识库文件, 并未rag打上tag和name

### CRUD-Controller

其实在实际阅读的时候, 这种CRUD的方法都可以直接跳过, 毕竟不涉及业务逻辑, 大部分也不会什么细节设计, 简单读下注释和@Resource发现只有dao的时候就大概知道可以跳过了, 但是需要在这个地方重点关注**CRUD处理的对象**, 需要有个印象, 同时删除掉id/name/createtime之类的属性, 留下来需要重点关注的和业务相关的属性记录下来

#### AiAdminAgentClientController

---

```java
@RequestMapping(value = "queryAgentClientList", method = RequestMethod.POST)
    public ResponseEntity<List<AiAgentClient>> queryAgentClientList(@RequestBody AiAgentClient aiAgentClient) {
        List<AiAgentClient> aiAgentClientList = aiAgentClientDao.queryAgentClientList(aiAgentClient);
        return ResponseEntity.ok(aiAgentClientList);
    }
```

方法内部就是简单的根据入参获取所有AI智能体客户端, 根据SQL语句中的查询条件, 可以发现这里是通过传入的aiAgentClient中的有的信息来搜索出来所有满足条件的client, 这里的入参就像是一些软件上的筛选标签, 只有这些tag的结果才会被显示出

> 通过这个方法能实现根据条件获取所有满足条件的AiAgentClient的业务, 可以通过这个实现最开始的所有的client的展示, 只要入参是空就行, 也就是没有筛选条件
>
> 同时是分页返回(这里因为使用了offset, 会有深度分页的问题, 不过就这个查询体量这个问题还不需要被关心就是了, 不会影响啥性能)

---

剩下的方法就是同样的CRUD, 不额外说明

- ​     根据ID查询AI智能体客户端关联
- ​     根据智能体ID查询客户端关联列表
- ​     根据客户端ID查询智能体关联列表
- ​     新增AI智能体客户端关联
- ​     更新AI智能体客户端关联
- ​     删除AI智能体客户端关联

不过这里能发现智能体和客户端之间是有差异的, 同时通过client_id和agent_id连接

下面是这个Controller CRUD的实体对象的定义

```java
public class AiAgentClient extends Page {
    private Long id;
    private Long agentId;
    private Long clientId;
    /**
     * 序列号(执行顺序)
     */
    private Integer sequence;
    private Date createTime;
}
```

继承Page是为了便于实现分页

```java
public class Page {
    /**
     * 当前页码
     */
    private int pageNum = 1;
    /**
     * 每页条数
     */
    private int pageSize = 10;
    /**
     * 总条数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;
    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }
    public int getLimit() {
        return pageSize;
    }

}
```

#### AiAdminAgentController

这个Controller是对于AiAgent持久化对象的CRUD, 内容不赘述

     - 分页查询AI智能体列表
     - 根据channel查询AI智能体列表
     - 根据ID查询AI智能体
     - 新增AI智能体
     - 更新AI智能体
     - 删除AI智能体

需要关注的是AiAgent这个持久化对象的属性

```java
public class AiAgent extends Page {

    private Long id;
    private String agentName;
    private String description;

    /**
     * 渠道类型(agent，chat_stream)
     */
    private String channel;

    /**
     * 状态(0:禁用,1:启用)
     */
    private Integer status;
    
    private Date createTime;
    private Date updateTime;

}

```

需要后续重点关注的是channel属性的作用

#### AiAdminAgentTaskScheduleController

CRUD的方法

- 分页查询AI代理任务调度列表
- 根据ID查询AI代理任务调度
- 新增AI代理任务调度
- 更新AI代理任务调度
- 删除AI代理任务调度

- 只留下来的和业务代码高度相关的部分

```java
public class AiAgentTaskSchedule extends Page {
    
    /**
     * 任务描述
     */
    private String description;
    /**
     * 智能体ID
     */
    private Long agentId;
    /**
     * 时间表达式(如: 0/3 * * * * *)
     */
    private String cronExpression;
    
    /**
     * 任务入参配置(JSON格式)
     */
    private String taskParam;
    private Integer status;
}
```

这里看到我们能创建定时task, 定时执行某个agent, 通过cron表达式来设置定时任务的执行频次

#### AiAdminClientAdvisorConfigController

- 分页查询客户端顾问配置列表
- 根据ID查询客户端顾问配置
- 根据客户端ID查询顾问配置列表
- 新增客户端顾问配置
- 更新客户端顾问配置
- 删除客户端顾问配置

- 智能体顾问的配置对象

```java
public class AiClientAdvisorConfig extends Page {
    /**
     * 客户端ID
     */
    private Long clientId;

    /**
     * 顾问ID
     */
    private Long advisorId;
}
```

将client和advisor通过id关联起来

#### AiAdminClientAdvisorController

对AiClientAdvisord的CRUD

```java
class AiClientAdvisor extends Page {
    /**
     * 顾问类型(PromptChatMemory/RagAnswer/SimpleLoggerAdvisor等)
     */
    private String advisorType;
    /**
     * 扩展参数配置，json 记录
     */
    private String extParam;
    /**
     * 状态(0:禁用,1:启用)
     */
    private Integer status;

}
```

顾问类型, PromptChatMemory类型的功能应该是预定义了提示词, RagAnswer是前面的结合了Rag知识库的顾问, SimpleLoggerAdvisor是日志分析? 这个暂时不知道

CRUD的方法

- 分页查询客户端顾问列表
- 根据ID查询客户端顾问
- 新增客户端顾问
- 更新客户端顾问
- 删除客户端顾问

#### AiAdminClientModelConfigController

对AiClientModelConfig的CRUD

```java
public class AiClientModelConfig extends Page {
    /**
     * 客户端ID
     */
    private Long clientId;

    /**
     * 模型ID
     */
    private Long modelId;
}

```

将Client与model绑定起来的config类, 用于持久化这个client使用的模型?

CRUD的方法

- 分页查询客户端模型配置列表
- 根据ID查询客户端模型配置
- 根据客户端ID查询模型配置
- 根据模型ID查询客户端模型配置列表
- 新增客户端模型配置
- 更新客户端模型配置
- 删除客户端模型配置

#### AiAdminClientModelController

对AiClientModel的CRUD

AiClientModel: 标准的模型API配置类, 其中需要额外解释的是`completionsPath`和`embeddingsPath`, 同时需要将其与AiClientModelConfig区分开来, 后者只是为了建立model和client的关联关系的对象

- completionsPath: 用于支持对话功能, 指定AI模型文本生成/对话完成功能的API端点路径, 用于构建完整的API请求URL，调用模型的文本生成服务
- embeddingsPath: 用于支持rag功能, 指定AI模型向量嵌入功能的API端点路径, 用于将文本转换为向量表示，支持语义搜索、相似度计算等功能

```java
public class AiClientModel extends Page {
    
    /**
    * 主键ID
    */
    private Long id;

    /**
     * 模型名称
     */
    private String modelName;

    /**
     * 基础URL
     */
    private String baseUrl;

    /**
     * API密钥
     */
    private String apiKey;

    /**
     * 完成路径
     */
    private String completionsPath;

    /**
     * 嵌入路径
     */
    private String embeddingsPath;

    /**
     * 模型类型(openai/azure等)
     */
    private String modelType;

    /**
     * 模型版本
     */
    private String modelVersion;

    /**
     * 超时时间(秒)
     */
    private Integer timeout;

    /**
     * 状态(0:禁用,1:启用)
     */
    private Integer status
}
```

CRUD的方法

- 分页查询客户端模型列表
- 查询所有的模型的配置
- 根据ID查询客户端模型
- 新增客户端模型
- 更新客户端模型
- 删除客户端模型

#### AiAdminClientSystemPromptController

对AiClientSystemPrompt的CRUD

AiClientSystemPrompt: 提示词持久化对象

```java
public class AiClientSystemPrompt extends Page {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 提示词内容
     */
    private String promptContent;
    /**
     * 状态(0:禁用,1:启用)
     */
    private Integer status;
}
```

CRUD的方法

- 分页查询系统提示词列表
- 查询所有系统提示词列表
- 根据ID查询系统提示词
- 新增系统提示词
- 更新系统提示词
- 删除系统提示词

#### AiAdminClientToolConfigController

对AiClientToolConfig的CRUD

AiClientToolConfig: 用于将tool和client_id关联起来, 同时说明这个工具的类型, 有MCP和function call作为可选项

```java
public class AiClientToolConfig extends Page {
    /**
     * 客户端ID
     */
    private Long clientId;
    /**
     * 工具类型(mcp/function call)
     */
    private String toolType;

    /**
     * 工具ID(MCP ID/function call ID)
     */
    private Long toolId;
}
```

CRUD方法

- 分页查询客户端工具配置列表
- 根据ID查询客户端工具配置
- 根据客户端ID查询工具配置列表
- 新增客户端工具配置
- 更新客户端工具配置
- 删除客户端工具配置

#### AiAdminClientToolMcpController

对AiClientToolMcp的CRUD

AiClientToolMcp: 对于MCP工具的配置信息的记录, 其中需要注意的字段是`transportType`和`transportConfig`

**transportType:** 传输类型(sse/stdio)

- SSE(Server-Sent Events): 使用远程MCP服务, 比如百度AI搜索这种, 往往需要有apikey进行验证
  - 基于HTTP的实时通信协议
  - 适用于远程MCP服务器
  - 通过HTTP连接进行数据传输
- STDIO: 本地部署的MCP服务
  - 基于进程间通信
  - 适用于本地MCP服务器
  - 通过命令行启动和管理

**transportConfig:** 传输配置, 简单来说就是MCP的一切配置信息

1. 连接参数
   - SSE模式：配置远程服务的URL、端点路径、认证信息
   - STDIO模式：配置本地进程的启动命令和参数
2. 认证信息
- API密钥、令牌等安全凭证
- 支持不同服务商的认证方式
3. 服务发现
- 指定具体的MCP服务器地址
- 配置服务端点和路径
4. 运行环境
- STDIO模式下的JVM参数
- 进程启动环境变量

```java
public class AiClientToolMcp extends Page {
    /**
     * MCP名称
     */
    private String mcpName;
    /**
     * 传输类型(sse/stdio)
     */
    private String transportType;
    /**
     * 传输配置
     */
    private String transportConfig;
    /**
     * 请求超时时间(分钟)
     */
    private Integer requestTimeout;

    /**
     * 状态(0:禁用,1:启用)
     */
    private Integer status;
}
```

CRUD方法

- 分页查询MCP配置列表
- 根据ID查询MCP配置
- 新增MCP配置更新MCP配置
- 更新MCP配置
- 删除MCP配置

#### AiAdminRagOrderController

对AiRagOrder的CRUD

AiRagOrder: 知识库的订单, 这里的订单不是真的什么下单的那个订单, 这里只是一种抽象的业务模型, 下单-处理业务-返回处理结果

```java
public class AiRagOrder extends Page {
     /**
     * 主键ID
     */
    private Long id;

    /**
     * 知识库名称
     */
    private String ragName;
    /**
     * 知识标签
     */
    private String knowledgeTag;
    /**
     * 状态(0:禁用,1:启用)
     */
    private Integer status;
}
```

CRUD的方法

- 分页查询RAG订单列表
- 查询所有RAG订单列表
- 根据ID查询RAG订单
- 新增RAG订单
- 更新RAG订单
- 删除RAG订单

## 总结

- taskSchedule: 定时任务调度, **task绑定agentId**, 通过corn表达式设置任务定时策略, 通过taskParam配置, 这里的业务流程可能是通过业务系统创建定时任务, 根据配置信息, 调用某个agent执行配置信息中的任务
- Advisor: 存在多种类型的顾问, 调用知识库的, 带prompt的, 还有一个未知的simplelogger, 同时**advisor绑定client**, advisor可以看作是提供了提问的配置信息, 你可以根据不同的配置来进行不同类型的提问
- model: 模型的配置信息, 一个配置信息也就代表了一个模型实体, **model和client绑定**
- Tool: AI可以使用的工具(MCP等)的配置信息, **tool和client绑定**
- Rag: 存储在PG数据库里面, MySQL里面只记录了id, name, tag等信息\
- client: **绑定advisor, model, tool**, 一个AI服务的实体, 定义了这个服务使用的tool, advisor配置信息, model模型配置信息
- agent: 绑定多个client, 是一个综合AI服务的实体, 一个agent能通过绑定多个client, 并定义执行顺序, 完成对多个client的串联, 实现复杂的AI服务






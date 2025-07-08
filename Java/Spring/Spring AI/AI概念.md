







## Models

我们按照输入和输出的内容, 将模型分成四大类

![Model types](https://docs.spring.io/spring-ai/reference/_images/spring-ai-concepts-model-types.jpg)

## Prompt

 基于自然语言输入, 用于知道AI模型生成特定的内容

- 用户设定的规则
- 输出的格式
- 不同角色的不同输入

创建高效的提示词, 包括建立请求的上下文并将其中的各个部分替换成特定用户的输入的值

`Tell me a {adjective} joke about {content}`

## Embedding

Embedding的工作原理是将文本, 图像, 视频转换成浮点数向量

通过计算两段文本向量之间的数值距离, 应用程序可以衡量两个向量也就是两个内容之间的相似度

常用于实现RAG (Retieval Augmented Generation: 检索增强生成)  

## Structured Output

简单来说就是对于怎么让AI的输出按照固定的格式的技术

## Bring Your Data & APIs to the AI Model

- Prompt Stuffing : 将数据嵌入到提供给模型的提示中, 典型的应用之一就是RAG
- Tool Calling: 工具调用, 允许AI使用外部系统API的工具

### RAG

RAG可以分成两个阶段

- 将非结构化的数据加载到矢量数据库中
  1. 将文档按照内容的语义边界来拆分成多个部分
  2. 将文档的各个部分进一步拆分成大小为AI Token limit下的更小部分
- 处理用户输入
  1. 针对用户的输入, 从向量数据库中取出来相似的文档片段放入到AI的提示词中

### Tool Calling 

简单来说就是给了LLM调用工具, 访问最新的数据, 修改数据的功能

1. 对于MCP服务的服务方, 需要注册好服务
2. 对于客户端
   1. 在chat请求的时候包含工具的定义: 输入参数的名称, 描述和方案
   2. 调用工具
   3. 处理返回, 将其作为附加上下文生成响应

## Evaluating AI responses

用于评估AI系统的输出, 确保最终应用程序的准确性和有用性
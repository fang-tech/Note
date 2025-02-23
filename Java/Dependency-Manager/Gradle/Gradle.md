---
tags:
  - Java
  - build-tool
  - Gradle
---
# Gradle
## 1. Gradle简介

### 1.1 为什么有了maven, 仍然需要有Gradle

- Maven的构建项目需要依赖于其plugin, 构建过程固定, 对于大型项目多模块的构建, Maven的 "一键" 构建不够灵活
- Gradle构建速度快于Maven
- 大型项目和以及现在越来越多的公司都在使用Gradle

### 1.2 Gradle项目目录结构

- project
    - build : 封装编译后的字节码, 打成的包
    - gradle : 封装包装器文件夹
        - wrapper
            - gradle-wrapper.jar
            - gradle-wrapper.properties
    - src
    - gralew
    - gradlew.bat 包装器启动脚本
    - build.gradle 构建脚本, 类似于pom.xml
    - setting.gradle 设置文件, 定义项目及子项目名称信息, 和项目是一一对应关系

![[images/QQ20241223-113254.png]]

> gradlew与gradlew.bat执行的指定wrapper版本中的gradle指令,不是本地安装的gradle指令

### 1.3 配置Gradle

> 配置环境变量

1. 配置bin目录
    1. 配置系统变量GRADLE_HOME
    2. 配置Path : %GRADLE_HOME%/bin
    3. 配置仓库地址GRADLE_USER_HOME

>  修改maven下载源

1. 在Gradle, init.d文件夹中创建init.gradle文件
```
  allprojects { 
	repositories { 
		mavenLocal() 
		maven { name "Alibaba" ; url "https://maven.aliyun.com/repository/public" } 
		maven { name "Bstek" ; url "https://nexus.bsdn.org/content/groups/public/" } 
		mavenCentral() 
	}
	
	buildscript { 
		repositories { 
			maven { name "Alibaba" ; url 'https://maven.aliyun.com/repository/public' } 
			maven { name "Bstek" ; url 'https://nexus.bsdn.org/content/groups/public/' } 
			maven { name "M2" ; url 'https://plugins.gradle.org/m2/' } 
		} 
	} 
}
```

### 1.4 Gradle Wrapper

>  是一个包封装器, 解决的问题是不同项目需要不同版本的Gradle, 像是这个项目的虚拟gradle环境, 被封装到了这个包里

- 项目里面如果有了gradle wrapper, 我们使用这个项目的时候, 本地是可以不配置gradle的, 我们可以通过使用 gradlew 命令来执行编译等操作

> 控制本项目的Wrapper的生成

`--gradle-version=...` => 指定使用的gradle版本
`--gradle-distribution-url=` => 指定下载地址
`gradle warpper --gradle-version=..` 升级wrapper版本号,  但是并没有执行下载操作, 只是修改了gradle.properties中wrapper版本号
`gradle wrapper --gradle-version 5.2.1 --distribution-type all` => 关联源码用

> wrapper执行流程

1. 执行`.gradlew build`命令的时候, 如果对应版本的gradle不存在, gradle会读取gradle-wrapper.properties中的配置信息
2. 下载并解压到GRADLE_USER_HOME下的wrapper/dists目录
3. 并构建本地缓存, 下次再使用, 就无需重复下载 (GRADLE_USER_HOME/caches)
4. 之后执行./gradlew命令使用的都是指定的版本

> gradle-wrapper.properties文件解读

- distributionBase => 下载后Gradle压缩包解压后存储的目录
- distributionPath => 相对distributionBase的解压后的Gradle压缩包路径
- distributionUrl => 压缩包的下载地址
- networkTimeout
- validateDistributionUrl
- zipStoreBase => 存放zip压缩包的, 同distributionBase
- zipStorePath => 存放zip压缩包的, 同distributionPath

## 2. Gradle 与 IDEA整合


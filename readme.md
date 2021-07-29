

# TranspondSms-server 安卓手机短信转发工具服务器端-java实现

此软件（仅后端部分）希望使用者遵守**MPL**协议并鼓励有能力的人通过此软件获利，同时希望获得一定程度的赞助来实现之后也许想要的功能，同时来减轻作者进一步深造学习的生活压力。





大家好，这里是[雪思雨-法伊的由伊](https://www.finnewworld.top/)，该工具是适配[TranspondSms](https://github.com/xiaoyuanhost/TranspondSms)的服务器后端，由于该作者使用php开发，而本人无法成功编译并在服务器上运行，因此决定重写。首先是给直接用打算直接宝塔面板搭建的新手的快速教程：

## 快速使用与启动

## 环境要求

### 硬件环境

300+M内存的服务器

## 系统环境

1. 操作系统：Ubuntu 18，Ubuntu 20

2. 使用ssh等工具连接你的服务器（我个人推荐mobaxterm）

3. 安装java 11

   ```text
   sudo apt update
   sudo apt install openjdk-11-jdk -y
   //然后运行
   java -version
   如果有版本号之类的出现说明安装成功。
   ```

4. 安装宝塔面板

   `wget -O install.sh http://download.bt.cn/install/install-ubuntu_6.0.sh && sudo bash install.sh`

   宝塔面板会让你输入很多必要信息，根据需要输入即可，安装会需要一些时间，安装完成之后，会强制要求注册或者登录，如果是萌新请老老实实注册。然后登录之后，在安全中开放端口3001。

5. 接下来新建一个网站，域名如果有打算用的就填写打算用的域名，如果没有的话随意，我之后以a.com为例了。在这个里面打开数据库，数据库数据类型选择utf8mb4(默认是utf8的那个)其他全面默认即可。

6. 把在release中的sql文件下载，一共三个，然后导入新建的数据库执行即可。

7. 把jar包放到服务器上

   1. 在github的release中找到最新打包版本中的.jar文件，下载该文件。

   2. 右键，用你电脑上有的压缩软件里面的打开压缩包功能（不是解压），然后进入BOOT-INF，打开classes，可以看到一个application.properties，用记事本或者其他文本编辑器打开，然后你会看到这样的内容：

      ```
      spring.datasource.url=jdbc:mysql://localhost:3306/<你的数据库名>
      spring.datasource.username=<你的数据库用户名>
      spring.datasource.password=<你的数据库密码>
      ```

      然后把宝塔面板生成的对应内容填入，注意尖括号需要被删除！！

   3. 接下来保存，然后有些压缩软件会提示你是否保存修改，你选择保存。

   4. 然后上传，如果你对linux不熟悉，那么请直接上传到/root目录下，这样可以最大程度防止你找不到东西。

8. 运行

   `nohup java -jar sms-0.0.1-SNAPSHOT.jar 2>&1 &`

   目前我并没有实现开机自动启动等功能，所以如果重启服务器后，请直接重新使用上面的命令。

   接下来使用

   `tail -20 nohup.out`

   如果看到没有意外退出，那么就是正常启动了。



# 使用服务

连接拼接：http://<你的ip>:3001/

因为目前没有前端，所以所有的操作需要用工具完成。具体请参考之后的接口文档。

**注意：** 所有删除为伪删除，数据库中所有短信内容数据为加密保存，但是在日志文件中所有的操作为明文保存。



# 使用nginx封住ip实现反向代理。

直接可以用的：

    location ^~/api/msg/ {
          proxy_redirect off;
          proxy_set_header Host $host;
          proxy_set_header X-Real-IP $remote_addr;
          proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
          proxy_pass http://127.0.0.1:3001/;
          proxy_set_header Upgrade $http_upgrade;
          proxy_set_header Connection upgrade;
          proxy_http_version 1.1;
    }

其他内容使用宝塔基本配置即可。



# 接口文档

**注意** ：所有的接口传参如果没有使用该参数请不要传该内容，或者使用null，绝对不要使用空字符串。返回的内容会被一个message包裹，以下内容全部是data中的内容，而非直接回传的内容。

## 用户模块

### 数据库表

| 字段        | 说明           | 备注                                                         |
| ----------- | -------------- | ------------------------------------------------------------ |
| user_id     | 主键           |                                                              |
| login_phone | 手机号         |                                                              |
| login_email | 邮箱           |                                                              |
| password    | 密码           |                                                              |
| token       | 用户token      | 请前端在登录时获得该字段后始终保存该字段                     |
| create_time | 创建时间       |                                                              |
| update_time | 更新时间       |                                                              |
| is_deleted  | 是否被删除     |                                                              |
| secret_key  | 找回密码用密钥 | 由于并不打算提供找回密码服务，所以该密钥如果核验正确则能修改密码 |

### 方法接口

| 方法名和说明                                                 | 传参                                                         | 返回内容                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| Post  loginRegister 登录/注册<br/>传参的时候传电话或者邮箱一个即可，因为并不会实际去校验也不会用于找回密码，但是手机号码和邮箱格式必须正确，请前端同学校验后再传参<br/>对应返回值，在注册的时候会返回secret_key，请用很明显的方式告知用户该字段的值，并要求自行保存，因为只会返回给前端一次，返回json中的token请置于request header中，在之后的请求中请携带该token。 | {<br/>  "loginPhone": "电话号码",<br/>  "loginEmail": "邮箱",<br/>  "password": "密码"<br/>} | {<br/>  "userId": 59,<br/>  "userName": "fake_data",<br/>  "token": "fake_data",<br/>  "secretKey": "fake_data"<br/>} |
| Post delAccount 删除用户<br/>请在json中传递token。           | {<br/>  "token": ""<br/>}                                    |                                                              |
| @Post resetPassWordBySecretKey通过密钥找回密码<br/>返回内容会提供新的token，请在这个时候用明显的表示告知用户新的secretkey。 | {<br/>  "loginPhone": 84,<br/>  "loginEmail": "fake_data",<br/>  "secretKey": "fake_data",<br/>  "password": "fake_data"<br/>} | {<br/>  "token": "",<br/>  "secretKey": "",<br/>  "password": ""<br/>} |
| @Post resetPassWordByToken登录后根据token修改密码<br/>返回内容会提供新的token，请在这个时候用明显的表示告知用户新的secretkey。 | {<br/>  "password": "fake_data",<br/>  "token": "fake_data"<br/>} | {<br/>  "password": "fake_data",<br/>  "token": "fake_data"<br/>} |



## 消息通道模块

一旦channel被删除同时不记得加密密文，那么数据库中的短信内容就无法被获取了。

### 数据表

| channel_id             | 主键         |                                  |
| ---------------------- | ------------ | -------------------------------- |
| user_id                | 使用用户id   |                                  |
| channel_token          | 通道token    |                                  |
| channel_name           | 通道名称     |                                  |
| channel_encryption_key | 通道加密密码 | 如果没有那么数据库中数据不会加密 |
| create_time            | 生成时间     |                                  |
| is_deleted             | 是否被删除   |                                  |



### 对象


| 方法+方法信息                                                | 传参                                                         | 返回值                                                       |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| Post  getChannelList获得通道列表                             | {<br/>  "userToken": "fake_data"<br/>}                       | 一个大大的list！                                             |
| Post delChannel 删除通道<br/>传参为通道id                    | {<br/>  "id": 7,<br/>  "token": "fake_data"<br/>}            |                                                              |
| Post addChannel新增通道<br/>如果不使用通道加密密钥的话请不要传参给后端。 | {<br/>  "channelName": "fake_data",<br/>  "channelEncryptionKey": "fake_data",<br/>  "token": "fake_data"<br/>} | {<br/>  "channelToken": "fake_data",<br/>  "channelName": "fake_data",<br/>  "channelEncryptionKey": "fake_data"<br/>} |

## 短信表

| id            | 总id       |                                          |
| ------------- | ---------- | ---------------------------------------- |
| channel_token | 通道名     |                                          |
| from_place    | 来源手机   |                                          |
| content       | 内容       |                                          |
| timestamp     | 插入时间   |                                          |
| sign          | 签名       | 这个签名是加密后的用来和时间戳一起验签的 |
| create_time   | 插入时间   |                                          |
| is_deleted    | 是否被删除 |                                          |

## 方法接口

+ 获取信息列表

  使用websocket实现，启动链接格式为：

  ws://<你的域名>:3001/getMsgList/<通道id>

  请求获取信息时请发送一条内容格式为json字符串的内容

  ```
  {
    "channelToken": "fake_data",
    "userToken": "fake_data",
    "heartBeat": false
  }
  ```

  heartBreat如果为true则单纯为心跳会返回发出的字符串，如果为false，则会去请求数据。

  ，后端判断启动链接中的通道id和对应channeltoken相等后，会返回相应内容。

  

  

  收到消息的格式类似于：

  ```
  [{"channelToken":"i4lnd649vinc5yqyvmomtkqu","content":"15536695887\n你好\n来自 红色白皮手机 卡： 卡哇伊 \n2021-07-22 13:03:45","createTime":1626973428000,"fromPlace":"15536695887","id":47,"sign":"5YwYJSmFUwzsNbOs1Cvu3w5qy7cT1jq6v0zEI5iquvI%3D","timestamp":1626930225000},{"channelToken":"i4lnd649vinc5yqyvmomtkqu","content":"test@2021-07-22 13:32:25","createTime":1626975148000,"fromPlace":"TranspondSms test","id":50,"sign":"RhzknGo0Py%2FPMlWExQVHm2Sn9EwEDJyp%2Bwa4qocSXq0%3D","timestamp":1626931945000},{"channelToken":"i4lnd649vinc5yqyvmomtkqu","content":"测试信息","createTime":1626975314000,"fromPlace":"15029968007","id":51,"sign":"E4vdGQQLLTCpWEhvYJFe038wOKm35TZBN46E9Ik5%2BTQ%3D","timestamp":1626769778000},{"channelToken":"i4lnd649vinc5yqyvmomtkqu","content":"测试信息","createTime":1626982739000,"fromPlace":"15029968007","id":53,"sign":"E4vdGQQLLTCpWEhvYJFe038wOKm35TZBN46E9Ik5%2BTQ%3D","timestamp":1626812978000}]
  ```

  如果在链接开启时收到手机推动的消息，那么会收到如下格式的内容：

  ```
  [{"channelToken":"i4lnd649vinc5yqyvmomtkqu","content":"test@2021-07-22 13:32:25","fromPlace":"TranspondSms test","id":50,"sign":"RhzknGo0Py%2FPMlWExQVHm2Sn9EwEDJyp%2Bwa4qocSXq0%3D","timestamp":1626931945000}]
  ```

+ 手机发送接口格式

  ```
  Post pushMsg
  ```

  **其他内容参照：**

  url: https://api.sl.allmything.com/api/msg/pushMsg?token=p9EM2K4Po01UIJr3sISbRmBFYWCHOGQaqwqk6cgxdsfyevTXtz8hVUlNAunD5i

  请求体如下

  > post form 参数：

  | key       | 类型   | 说明                                                         |
  | --------- | ------ | ------------------------------------------------------------ |
  | from      | string | 来源手机号                                                   |
  | content   | string | 短信内容                                                     |
  | timestamp | string | 当前时间戳，单位是毫秒，（建议验证与请求调用时间误差不能超过1小时，防止重放欺骗） |
  | sign      | string | 当设置secret时，生成的sign签名，用于发送端校验，规则见下方sign校验规则 |

+ 清除消息

  ```
  Post clearMsg
  {
  	"channelToken":",
  	"id":12
  }
  ```

  大概是这样，这个功能我没测试，如果有bug请告诉我。id为所有已经获取的信息里面最大的信息id号。
  
  提供一个测试环境，但是请不要使用该服务器为该主要使用，因为该服务器仅提供给 异世界悠闲农家简体化组 成员、赞助群友等相关朋友使用，且该服务器由这些人赞助搭建。
  
  https://dashucunsms.cyou/api/msg/
  
  之后是按接口文档中的内容测试即可。


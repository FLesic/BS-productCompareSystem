# 使用手册

## 1. 项目概述

### 1.1 项目说明

项目名称：商品比价网站

任务提出者：浙江大学BS体系结构设计任课教师——胡晓军

开发者：浙江大学 任飞扬 3220102754

用户群：有商品查询需求的用户群体

### 1.2 项目背景

随着互联网技术的飞速发展，电子商务已成为人们日常生活的重要组成部分。各类电商平台如雨后春笋般涌现，提供了丰富的商品选择。然而，这也带来了新的问题：用户在面对琳琅满目的商品时，往往难以快速找到最适合自己的选项。不同平台间的商品信息各异，价格、质量、用户评价等信息分散，缺乏统一的比较机制。因此，开发一个多平台商品比较系统，旨在帮助用户高效整合并分析来自多个电商平台的商品信息，提升购物体验和决策效率，具有重要的现实意义和应用价值。

### 1.3 项目功能

本项目基本的功能包括：

* 用户登录
* 用户注册
* 用户个人信息修改
* 商品搜索
* 商品详情查看与价格对比
* 商品收藏与降价提醒

### 1.4 使用手册目的

* **指导用户操作**：提供详细的步骤和说明，帮助用户理解和使用产品或系统的各项功能。
* **提高用户体验**：通过清晰的指导减少用户在使用过程中的困惑和错误，提升整体的用户体验。
* **减少技术支持需求**：通过提供常见问题的解决方案和操作指南，减少用户对技术支持的依赖。
* **提升产品专业性**：展示产品的复杂功能和高级特性，提升用户对产品专业性的认知。

## 2. 安装配置

### 2.1 环境准备

#### 2.1.1 数据库环境（MySQL）

确保本地安装了Mysql数据库，建议版本8.0.0以上

#### 2.1.2 后端环境（SpringBoot）

* Java Development Kit（JDK）：确保已安装JDK 17或更高版本

  > 推荐使用JDK 22
  >
* Maven：安装并配置Maven以管理项目依赖，建议版本3.9.6以上

#### 2.1.3 前端环境（Vue3）

* Node.js：确保已经安装Node.js，建议版本v20.12.2
* npm：确保已经安装npm，建议版本10.7.0

### 2.2 数据库配置（MySQL）

请确保本地拥有MySQL数据库，同时按照下述指令完成数据库和表的创建：

首先创建一个名为productcomparesystem的数据库并将其设置为当前活动的数据库：

```sql
create database productcomparesystem;
use productcomparesystem;
```

创建用户表：

```sql
CREATE TABLE user (
	user_id INT AUTO_INCREMENT PRIMARY KEY,
	user_name VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	password VARCHAR(50) NOT NULL,
    phone VARCHAR(50) NOT NULL
);
```

创建商品表：

```sql
CREATE TABLE product (
	product_id VARCHAR(500) NOT NULL PRIMARY KEY,
    product_name VARCHAR(1000) NOT NULL,
    price DOUBLE NOT NULL,
    platform VARCHAR(50) NOT NULL,
    shop VARCHAR(100) NOT NULL,
    photo_url VARCHAR(5000) NOT NULL,
    detail VARCHAR(500) NOT NULL,
    product_url VARCHAR(5000) NOT NULL
);
```

创建收藏表：

```sql
CREATE TABLE collect (
    collect_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    product_id VARCHAR(500) NOT NULL,
    isLowReminder TINYINT(1) DEFAULT 0 NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);
```

创建历史价格表：

```sql
CREATE TABLE history (
	history_id INT AUTO_INCREMENT PRIMARY KEY,
    history_date DATE,
    price DOUBLE NOT NULL,
    product_id VARCHAR(500) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);
```

### 2.3 后端配置（SpringBoot）

#### 2.3.1 配置application.properties

在`src/main/resources/application.properties`​文件中配置数据库连接和其他SpringBoot属性。示例配置如下：

```properties
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
```

#### 2.3.2 加载Maven

建议使用IDEA工具，加载pom.xml内的相关依赖。如果出现报错问题，请检查你的maven版本以及JDK版本。

#### 2.3.3 运行后端项目

IDEA中点击SpringbootBackendApplication的右侧箭头运行项目

​![image](assets/image-20241215161132-781kun8.png)​

### 2.4 前端配置（Vue3）

#### 2.4.1 安装项目依赖

在`vue-frontend`​目录下运行以下命令安装项目依赖：

> npm install

#### 2.4.2 运行Vue项目

在`vue-frontend`​目录下运行以下命令启动前端Vue：

> npm run dev

## 3.操作说明

### 3.1 用户登录

#### 3.1.1 功能简述

用户登录功能的主要目的是验证用户的身份，确保用户能够安全地访问其个人账户和相关数据。用户需要输入之前注册过的邮箱或用户名作为账户，并输入对应的密码完成登录。

#### 3.1.2 操作流程

首先登录网站会出现用户登录界面：

​![image](assets/image-20241215112220-94srnes.png)​

输入正确的用户名和密码后点击登录：

​![image](assets/image-20241215112505-yzqva94.png)​

或者输入正确的邮箱名和密码后点击登录：

​![image](assets/image-20241215113046-3bq0f1t.png)​

显示正确跳转至搜索界面：

​![image](assets/image-20241215112605-0rf5gww.png)​

用户信息显示正确：

​![image](assets/image-20241215112556-sme1uju.png)​

#### 3.1.3 常见问题和解决方法

1. **提示请输入密码**​

    ​![image](assets/image-20241215112652-tgmv7b3.png)​

    用户尚未输入密码，输入对应密码后点击登录验证用户信息

2. **密码错误**

    ​![image](assets/image-20241215112748-6ncm0ch.png)​

    账户存在但是密码输入错误，请重新输入密码点击登录验证用户信息
3. **用户名或邮箱不存在**

    ​![image](assets/image-20241215112949-kdyuotn.png)​

    用户名或邮箱不存在，请点击注册链接完成注册

### 3.2 用户注册

#### 3.2.1 功能简述

用户注册功能的主要目的是收集必要的用户信息，创建一个独特的用户账户，以便用户可以访问特定的服务和功能。用户需要输入用户名，邮箱，电话以及设置相应密码，通过邮箱验证码正确验证后完成注册。

一些注意点：

* 用户名：至少三个字符
* 邮箱：输入正确的邮箱格式，目前仅支持QQ邮箱
* 电话：输入正确的11位电话号码
* 密码：至少6个字符
* 确认密码：需要和密码一致

#### 3.2.2 操作流程

在登录网站中点击链接"首次使用？点我注册！"进入用户注册页面：

​![image](assets/image-20241215113834-hgfwv0z.png)​

输入您喜欢的用户名（至少三个字符），输入正确的邮箱（目前仅支持QQ邮箱），输入11位手机号码。设置个人密码（不少于6个字符），完成密码的确认：

​![image](assets/image-20241215114120-wsp39f6.png)​

点击获取验证码，系统会向您注册的邮箱中发送6位数字的验证码：

​![image](assets/image-20241215114543-tupruxq.png)​

查看邮箱，获取验证码：

​![image](assets/image-20241215114826-a434ctr.png)​

正确输入验证码后点击注册跳转：

​![image](assets/image-20241215114853-nzlr57t.png)​

用户信息正确录入，注册成功：

​![image](assets/image-20241215114805-8diqms6.png)​

#### 3.2.3 常见问题和解决方法

1. **用户注册信息不完整**

    ​![image](assets/image-20241215114953-bz2p923.png)​

    请按照提示，填写每一个必要信息
2. **用户注册信息格式不正确**

    * 用户名不能少于3个字符：​![image](assets/image-20241215115055-zlh3fqx.png)​

    * 邮箱格式需要正确：​![image](assets/image-20241215115136-33ps9vo.png)​
    * 手机号需要是正确的11位电话号码：​![image](assets/image-20241215115209-38kln5q.png)​
    * 密码不能少于6位：​![image](assets/image-20241215115237-nl2h1zu.png)​
    * 确认密码需要和密码一致：

      ​![image](assets/image-20241215120018-mppov3s.png)​
3. **验证码失效或错误**

    如果未能在60s输入验证码，此验证码将过期，需要重新获取验证码

    ​![image](assets/image-20241215115534-ksmau4r.png)​

    验证码如果错误，请查看邮箱是否填写正确以及日期的查看，确保收到了平台发送的正确验证码

    ​![image](assets/image-20241215115647-mvmlvlp.png)​
4. **用户名已存在**

    ​![image](assets/image-20241215115815-b2bcth5.png)​

    其他用户已经使用过该用户名，请换一个用户名注册
5. **邮箱已存在**

    ​![image](assets/image-20241215115854-ejg1yg0.png)​

    该邮箱已经被注册使用，请换一个邮箱注册

### 3.3 用户个人信息修改

#### 3.3.1 功能简述

用户个人信息修改功能的主要目的是让用户能够轻松更新其账户信息，包括用户名、邮箱、电话、密码等，以确保信息的准确性和相关性。需要用户在其个人中心进行编辑操作，同时需要输入密码进行验证。

#### 3.3.2 操作流程

个人头像出现在界面的右上角，在商品搜索界面或者商品详情页面均有个人头像信息：

​![image](assets/image-20241215141937-i6uivy0.png)​

点击头像弹出下拉框，点击个人中心：

​![image](assets/image-20241215142006-br8ehgj.png)​

在弹出的个人用户信息卡片中点击编辑：

​![image](assets/image-20241215142028-h3ny5a0.png)​

需要用户输入原始密码确认身份：

​![image](assets/image-20241215142127-j8go7qy.png)​

之后用户可以修改自己的用户名和邮箱以及密码，依然需要保持相关的格式：

* 用户名至少三个字符且唯一
* 正确邮箱格式且唯一
* 正确11位电话号码
* 修改密码至少6位

这里以修改用户名为例：

​![image](assets/image-20241215142346-x1h8znm.png)​

修改好后点击确认验证，显示编辑成功：

​![image](assets/image-20241215142402-9xwrkhy.png)​

再次查看个人信息，信息已经成功修改：

​![image](assets/image-20241215142419-z8nljgt.png)​

#### 3.3.3 常见问题和解决方法

1. **原始密码不正确**

    ​![image](assets/image-20241215142508-h9lckyw.png)​

    请输入账户正确密码验证身份
2. **用户名或邮箱已存在**

    已经有其他用户创建了此用户名/邮箱，请修改用户名或邮箱
3. **请输入原始密码确认身份**

    ​![image](assets/image-20241215142632-em1oeve.png)​

    修改信息需要确认修改者的身份，通过输入原始密码完成，请重新输入正确的原始密码确认身份

### 3.4 商品搜索

#### 3.4.1 功能简述

商品搜索功能的主要目的是提供一个直观、高效的搜索体验，使用户能够通过关键词、类别、品牌或其他属性快速找到所需的商品。用户需要在商品搜索页面的搜索框中输入搜索信息，点击右侧Search图标即可开始搜索。

注：搜索需要等待一定时间

#### 3.4.2 操作流程

首先进入商品搜索页面：

​![image](assets/image-20241215142934-94qls6u.png)​

在商品搜索框中输入搜索关键信息，例如：牛肉干

​![image](assets/image-20241215143000-xctx36c.png)​

点击右侧的搜索图标开始搜索：

​![image](assets/image-20241215143016-2yorwk1.png)​

耐心等待一段时间后显示搜索的商品：

​![image](assets/image-20241215143049-jpezi1m.png)​

有主流平台的商品：

* 亚马逊平台（Amazon）：

  ​![image](assets/image-20241215143118-jogdslq.png)​
* 当当网（Dangdang）：

  ​![image](assets/image-20241215143145-j2chipy.png)​
* 苏宁易购网（SN）：

  ​![image](assets/image-20241215143225-gvq0tvp.png)​

下滑页面可以查看更多相关商品：

​![image](assets/image-20241215143249-ibyaeka.png)​

#### 3.4.3 常见问题和解决方法

1. **关键词内容搜索不出来**

    可能是网络问题，请重新输入再次尝试搜索；或者切换类似的关键词内容再次搜索。
2. **长时间加载**

    可能是服务器端爬取失败，请刷新页面后重新切换关键词搜索。

### 3.5 商品详情查看与价格对比

#### 3.5.1 功能简述

商品详情查看功能旨在提供详尽的商品信息，包括描述、规格、用户评价等，以帮助用户全面了解商品。用户在商品搜索页面中的商品卡片中选择感兴趣的商品，点击商品详情按钮查看商品详细内容。

同时商品详情内容页提供不同平台以及历史价格比价，帮助用户更好比较不同平台以及时间点上商品的价格，以找到最佳购买选项。

#### 3.5.2 操作流程

在商品搜索页面中选择自己感兴趣的商品：

​![image](assets/image-20241215144551-7md4ykv.png)​

点击该商品卡片中的商品详情按钮：

​![image](assets/image-20241215152201-itzgf7i.png)​

加载相关数据可能会花费一点时间，请耐心等待：

​![image](assets/image-20241215152211-j6jnsyf.png)​

商品详情页面如下：

​![image](assets/image-20241215152226-4bvuwzs.png)​

左侧是商品的详情信息，用户可以在此查看商品的完整名称、店铺来源、商品平台、商品价格、商品详细信息、商品图片，点击“去看看”按钮支持跳转到原链接网站查看更详细信息：

​![image](assets/image-20241215151707-ls2zxyh.png)​

右侧部分是商品的历史价格趋势图，方便用户比较不同时间点相同商品的价格：

​![image](assets/image-20241215144653-dd15c91.png)​

在商品详情页面的下部是不同平台比价，该功能聚焦于高度相似的商品在不同平台的价格比较：

* 点击亚马逊：

  ​![image](assets/image-20241215152240-3y8lwhc.png)​

  ​![image](assets/image-20241215152248-0lyssll.png)​
* 点击当当：

  ​![image](assets/image-20241215152301-0amkm6f.png)​

  ​![image](assets/image-20241215152309-xzy37bx.png)​

  > 可以看到高度相似的商品拥有价格的差异，方便用户筛选比较
  >
* 点击苏宁：

  ​![image](assets/image-20241215152322-u7rjiev.png)​

  ​![image](assets/image-20241215152332-7d65s7r.png)​

不同平台比价下的商品条目，支持点击商品名称查看详情内容：

​![image](assets/image-20241215145002-ich5qok.png)​

同时也支持点击右侧的“去看看”查看商品原始链接信息。

#### 3.5.3 常见问题和解决方法

1. **为什么历史价格趋势图时间跨度小？**

    直接从网站上获取历史信息较为困难，因此只有在每次启动服务器和搜索相关条目时才更新数据库，导致时间跨度可能会较小。
2. **不同平台价格比较，为什么有的平台没有相关商品？**

    由于平台的差异性，部分商品在其他平台上可能没有高度相关的商品。如有需要，请至平台官网上搜索。

### 3.6 商品收藏与降价提醒

#### 3.6.1 功能简述

商品收藏与降价提醒功能旨在提升用户体验，增加用户参与度。商品收藏功能允许用户保存他们感兴趣的商品，方便日后的商品内容查看和购买，用户不需要每次都搜索或浏览来找到同一商品，节省时间和精力；降价提醒功能帮助对价格敏感的用户在商品降价时获得通知，以便抓住购买时机。用户可以在商品详情页面中对商品进行收藏，同时设置降价提醒。用户之后可以在收藏商品查看自己收藏的商品。

#### 3.6.2 操作流程

在商品详情页面：

​![image](assets/image-20241215152347-1b60i92.png)​

点击收藏商品完成商品收藏：

​![image](assets/image-20241215152709-eo22rua.png)​

点击降价提醒设置降价提醒：

​![image](assets/image-20241215152731-wqzbr7g.png)​

同时支持用户取消提醒和收藏。

查看用户具体收藏，点击右上角人物头像，点击收藏商品：

​![image](assets/image-20241215152816-cmz4iaq.png)​

弹出用户的收藏商品，包括商品的名字、价格、商品平台、店铺名称等。用户可以单击商品名称查看详情内容，点击"去看看"按钮可以跳转到商品原链接查看内容。同时在这个页面中允许用户完成商品的提醒设置、收藏设置。

​![image](assets/image-20241215152825-2rn4t5s.png)​

如果商品降价了，会发送类似如此的信息：

​![image](assets/image-20241215153952-vq68vod.png)​

#### 3.6.3 常见问题和解决方法

1. **为什么一直收不到降价提示？**

    请耐心等待，商品价格的变化需要一定时期。
2. **为什么商品降价了收不到邮件？**

    请仔细审核个人信息中的邮件是否在注册之后发生了修改。

‍

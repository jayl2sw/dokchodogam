## 목차

- [기술 스택 & 버전 정보](#기술-스택-&-버전-정보)
- [빌드 방법](#빌드-방법)
- [배포 설정](#배포-설정)
- [기타 설정](#기타-설정)



## 기술 스택 & 버전 정보

1. 이슈 관리 : ![Jira](https://img.shields.io/badge/jira-%230A0FFF.svg?style=for-the-badge&logo=jira&logoColor=white)

2. 형상 관리: ![GitLab](https://img.shields.io/badge/gitlab-%23181717.svg?style=for-the-badge&logo=gitlab&logoColor=white)

3. 커뮤니케이션: ![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white) ![Discord](https://img.shields.io/badge/discord-5865F2.svg?style=for-the-badge&logo=discord&logoColor=white)

4. 개발 환경
   - IDE : ![IntelliJ](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)2022.1.2	![Visual_Studio_Code](https://img.shields.io/badge/Visual%20Studio%20Code-0078d7.svg?style=for-the-badge&logo=visual-studio-code&logoColor=white)1.71.2
   - DB : ![MySQL](https://img.shields.io/badge/MySQL-003545?style=for-the-badge&logo=mysql&logoColor=white) 8.0.30 ![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white) 6.0.1
   - UI & UX : ![Figma](https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white)
   - Server : ![AWS](https://img.shields.io/badge/AWS-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white) ![S3](https://img.shields.io/badge/S3-569A31?style=for-the-badge&logo=amazons3&logoColor=white) ![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=white) ![ubuntu](https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white)![NGINX](https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=white) ???
   
5. 상세
   - Backend : ![JAVA](https://img.shields.io/badge/JAVA-FF0000?style=for-the-badge&logoColor=white) 1.8 ![Spring](https://img.shields.io/badge/Spring boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)2.7.3 ![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)7.5 ![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)0.11.2 
   
   - Frontend : ![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)![CSS3](https://img.shields.io/badge/css3-1572B6?style=for-the-badge&logo=css3&logoColor=white)![JAVASCRIPT](https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white)
   
     ![Vue.js](https://img.shields.io/badge/vue.js-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white) 3.2.39 ![Node.js](https://img.shields.io/badge/Node.js-339933?style=for-the-badge&logo=node.js&logoColor=white)16.16.0 ![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=type&logoColor=white) 4.5.5 ![bootstrap](https://img.shields.io/badge/BootStrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white) 5.2.1 
   
   - CI/CD : ![docker](https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)20.10.18 ![Vue.js](https://img.shields.io/badge/jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white) 2.222.4
   
   - Data : ![python](https://img.shields.io/badge/python-3776AB?style=for-the-badge&logo=python&logoColor=white)3.8.10 ![hadoop](https://img.shields.io/badge/hadoop-66CCFF?style=for-the-badge&logo=apache hadoop&logoColor=white)3.3.2 ![spark](https://img.shields.io/badge/spark-E25A1C?style=for-the-badge&logo=apache spark&logoColor=white) 3.2.1 ![kafka](https://img.shields.io/badge/kafka-231F20?style=for-the-badge&logo=apache kafka&logoColor=white) 7.0.1 



## 빌드 방법



### Backend

1. Backend -> DockChoDoGam 폴더로 프로젝트 열기
2. JDK 버전 1.8 확인
3. src -> main -> DockChoDoGamApplication 클래스 실행

### Frontend

```vue
# 1. install
npm install --force
# 2. 실행
npm run serve
```



## 배포 설정

1. 서버 접속
2. 기본 설치

```bash
$ sudo apt install upgrade
$ sudo apt install update

# Java 설치
$ sudo apt install openjdk-8-jdk

# JAVA_HOME 설정
# ~/.bashrc에 export 추가
$ sudo vi ~/.bashrc
export JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:bin/java::")

# 적용
$ source ~/.bashrc

# 방화벽 설정
$ sudo ufw allow 22
# 22 port를 열어놓지 않으면 ssh로 접근 불가능하므로 꼭 열어야 한다.
$ sudo ufw enable
# 상태 확인
$ sudo ufw status
```

3. docker 설치 및 이미지 받기

```bash
$ sudo apt update

# http 패키지 설치
    $ sudo apt-get install -y ca-certificates \ 
    curl \
    software-properties-common \
    apt-transport-https \
    gnupg \
    lsb-release
    
# 레포지토리 설정
# Docker의 Official GPG Key 를 등록
$ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
# stable repository 를 등록
$ echo \
  "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
  
# docker 엔진 설치
$ sudo apt install docker-ce docker-ce-cli containerd.io

# docker 그룹에 사용자 추가
$ sudo usermod -aG docker ubuntu

# MySQL 이미지 다운로드
$ docker pull mysql

# MySQL Docker 컨테이너 생성 및 실행
$ docker run --name mysql-container -e MUSQL_ROOT_PASSWORD={password} -d -p 3306:3306 mysql:latest

# 도커 컨테이너 리스트 출력
$ docker ps -a

# MySQL Docker 컨테이너 접속
$ docker exec -it mysql-container bash

# Mongo DB 이미지 다운로드
$ docker pull mongo
$ docker run --name mongodb -d -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=dokcho -e MONGO_INITDB_ROOT_PASSWORD="{password}" mongo
$ docker exec -it mongodb /bin/bash
$ mongosh -u dokcho -p "{password}"

use admin
db.createUser({ user: 'dokcho', pwd: "{password}", roles: ['root'] })
```

4. jenkins 설치 및 실행

```bash
# Docker 이미지 다운로드
$ docker pull jenkins/jenkins:lts-jdk11

# 이미지 실행
$ docker run -d -p 20001:8080 -p 50000:50000 -v /var/jenkins:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock --name jenkins -u root jenkins/jenkins:lts-jdk11

# 현재 실행되어 있는 container 확인
$ docker ps

# 컨테이너 내부 접근
$ docker exec -it <container_id> bash
```

이후 {domain}:20001로 접속하여 jenkins 웹 접근

5. jenkins 내부에 docker 설치

```bash
$ curl https://get.docker.com/ > dockerinstall && chmod 777 dockerinstall && ./dockerinstall
```

6. ssl 설정

```bash
$ cd
$ sudo mkdir certbot
$ cd certbot
$ sudo mkdir conf www logs
$ sudo docker pull certbot/certbot
$ sudo docker run -it --rm --name certbot -p 80:80 -v "/home/ubuntu/certbot/conf:/etc/letsencrypt" -v "/home/ubuntu/certbot/log:/var/log/letsencrypt" -v "/home/ubuntu/certbot/www:/var/www/certbot" certbot/certbot certonly
```

이후 standalone, agree, no, <domain_name> 순서로 입력



7. nginx 설정(/home/ubuntu/nginx/conf/default.conf)

```bash
server{
        listen 80 default_server;
        listen [::]:80 default_server;

        server_name j7201.p.ssafy.io;

        return 301 https://$host$request-uri;
}

server{
        listen 443 ssl;
        listen [::]:443;

        server_name j7e201.p.ssafy.io;
        access_log /var/log/nginx/access.log;
        error_log /var/log/nginx/error.log;

        ssl_certificate /etc/letsencrypt/live/j7e201.p.ssafy.io/fullchain.pem;
        ssl_certificate_key /etc/letsencrypt/live/j7e201.p.ssafy.io/privkey.pem;
        ssl_protocols TLSv1 TLSv1.1 TLSv1.2 SSLv3;
        ssl_ciphers ALL;

		location / {
                root /usr/share/nginx/html;
                index index.html;
                try_files $uri $uri/ /index.html;
                proxy_redirect off;
                charset utf-8;
        }

        location /api {
                proxy_pass http://j7e201.p.ssafy.io:8080;
                charset utf-8;
                proxy_redirect off;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-NginX-Proxy true;
                proxy_set_header X-Forwarded-Proto $scheme;
        }

        location /oauth2 {
                charset utf-8;
                proxy_pass http://j7e201.p.ssafy.io:8080;
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Scheme $scheme;
                proxy_set_header X-Auth-Request-Redirect $request_uri;
        }

        location /login/oauth2 {
                charset utf-8;
                proxy_pass http://j7e201.p.ssafy.io:8080;
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Scheme $scheme;
                proxy_set_header X-Auth-Request-Redirect $request_uri;
        }

        location /game/arena/ingame {
                return 301 https://j7e201.p.ssafy.io/game;
        }
}

```

8. FrontEnd dockerfile

```bash
FROM node:lts-alpine as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install --force
COPY . .
RUN npm run build
# production stage
FROM nginx:stable-alpine as production-stage
COPY --from=build-stage /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

9. FrontEnd jenkins shell script

```bash
cd Frontend
cd dockchodogam

cp /secret/.env .env

docker build -t front .
docker ps -f name=front -q | xargs --no-run-if-empty docker container stop
docker container ls -a -f name=front -q | xargs -r docker container rm
docker ps -q --filter "name=front" | grep -q . && docker stop front && docker rm front | true
docker run -d -p 80:80 -p 443:443 -v /home/ubuntu/certbot/conf:/etc/letsencrypt/ -v /home/ubuntu/certbot/www:/var/www/certbot -v /home/ubuntu/nginx/conf/default.conf:/etc/nginx/conf.d/default.conf --name front front
docker rmi -f $(docker images -f "dangling=true" -q) || true
```

10. Backend dockerfile

```bash
FROM openjdk:8-jdk-alpine

ADD build/libs/DockChoDoGam-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar","-Dspring.profiles.active=gcp", "/app.jar"]
EXPOSE 8081
```

11. Backend jenkins shell script

```bash
cd Backend
cd DockChoDoGam
chmod +x ./gradlew
./gradlew build
docker ps -f name=backend -q | xargs --no-run-if-empty docker container stop
docker container ls -a -f name=backend -q | xargs -r docker container rm
docker build -t backend .
docker ps -q --filter "name=backend" | grep -q . && docker stop backend && docker rm backend | true
docker run -p 8080:8081 -d -e TZ=Asia/Seoul --name=backend backend -h bserver
docker rmi -f $(docker images -f "dangling=true" -q) || true
```



## 기타 설정

### Backend

#### 메일 전송(Gmail)

1. build.gradle

```java
implementation 'org.springframework.boot:spring-boot-starter-mail'
```

2. mail.properties

```java
mail.smtp.auth=true
mail.smtp.starttls.required=true
mail.smtp.starttls.enable=true
mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory
mail.smtp.socketFactory.fallback=false
mail.smtp.port=465
mail.smtp.socketFactory.port=465

#admin
AdminMail.id={계정}
AdminMail.password={앱 비밀번호}
```

3. 이메일 설정
   - 구글 계정 관리 -> 보안 -> 2단계 인증(사용), 앱 비밀번호(생성)
   - Gmail -> 빠른 설정 -> 모든 설정 보기 -> 전달 및 POP/IMAP -> 모든 메일에 POP 사용, IMAP 사용



#### S3

1. build.gradle

```java
implementation 'io.awspring.cloud:spring-cloud-starter-aws:2.3.1'
```

2. S3 버킷 생성

3. 사용자 추가 후 key 발급

4.  application-s3.properties

```java
#s3
cloud.aws.credentials.access-key={access key}
cloud.aws.credentials.secret-key={secret key}
cloud.aws.s3.bucket={bucket name}
cloud.aws.region.static={region}
cloud.aws.stack.auto=false
```



 #### 카카오 로그인

1. 카카오 developers에서 앱 생성
2. 플랫폼 설정 -> Web 플랫폼 등록 -> 사이트 도메인에 도메인 입력
3. Redirect URI 등록하러 가기 -> 카카오 로그인 활성화 On, Redirect URI(http://{domain}/login/oauth2/code/kakao) 등록
4. 메뉴바 -> 동의항목 -> 설정
5. 메뉴바 -> 보안 -> client secret 코드 생성
6. build.gradle

```java
implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
```

7. application-oauth.properties

```java
spring.security.oauth2.client.registration.kakao.client-id={client id}
spring.security.oauth2.client.registration.kakao.client-secret={secret}
spring.security.oauth2.client.registration.kakao.scope = profile_nickname, account_email
spring.security.oauth2.client.registration.kakao.client-name = Kakao
spring.security.oauth2.client.registration.kakao.authorization-grant-type = authorization_code
spring.security.oauth2.client.registration.kakao.redirect-uri = {redirect uri}
spring.security.oauth2.client.registration.kakao.client-authentication-method = POST

spring.security.oauth2.client.provider.kakao.authorization-uri = https://kauth.kakao.com/oauth/authorize
spring.security.oauth2.client.provider.kakao.token-uri = https://kauth.kakao.com/oauth/token
spring.security.oauth2.client.provider.kakao.user-info-uri = https://kapi.kakao.com/v2/user/me
spring.security.oauth2.client.provider.kakao.user-name-attribute = id
```



#### DB

1. application-db.properties

```java
spring.datasource.url=jdbc:mysql://{domain}:{port}/{db name}?serverTimezone=Asia/Seoul&useUnicode=true&characterEncoding=utf8
spring.datasource.username={username}
spring.datasource.password={password}
```



#### JWT

1. build.gradle
2. application-jwt.properties

```java
jwt.header=Authorization
jwt.secret={BASE64로 인코딩된 비밀키}
```



#### Plant.id

1. plant.id 접속
2. 서비스 신청 후 api key 획득
3. application-api.properties

```java
plant.api.key = {key}
```



### Hadoop 인프라 환경 구축

AWS 서버 6개를 이용하여 클러스터를 구축하여 사용할 수 있음

해당 포팅 매뉴얼은 Standalone 기준으로 작성 됨.

#### Hadoop Cluster 설정

##### 1. 하둡 설치

```bash
$ wget https://archive.apache.org/dist/hadoop/common/hadoop-3.3.2/hadoop-3.3.2.tar.gz
$ tar xvfz hadoop-3.3.2.tar.gz
```

##### 2. 하둡 설정

> ##### hadoop-env.sh 

```sh
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/jre/
```



> ##### core-site.xml
>
> ​	hdfs와 Yarn이 모두 사용

```xml
<configuration>
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://ssafy-e201:9000</value>
    </property>
    <property>
		<name>hadoop.http.staticuser.user</name>
    	<value>dokcho</value>
	</property>
</configuration>
```



> ##### hdfs-site.xml
>
> ​	namenode가 구동중인 서버의 메타 데이터가 저장되는 위치
>
> ​	datanode 서버의 대용량 데이터가 저장되는 위치
>
> ​	namenode가 체크포인트를 쓰는 곳

```xml
<configuration>
	<property>
        <name>dfs.namenode.name.dir</name>
        <value>file:///eco/hadoop3/dfs/name</value>
    </property>
    <property>
        <name>dfs.datanode.name.dir</name>
        <value>file:///eco/hadoop3/dfs/data</value>
    </property>
    <property>
        <name>dfs.namenode.checkpoint.dir</name>
        <value>file:///eco/hadoop3/dfs/namesecondary</value>
    </property>
</configuration>
```



> ##### yarn-site.xml

```xml
<configuration>
    <!-- host name 잡아줌 --> 
	<property>
    	<name>yarn.resourcemanager.hostname</name>
        <value>ssafy-e201</value>
    </property>
    <!-- port 변경이 필요할 경우, 공격을 막기 위해--> 
    <property>
    	<name>yarn.resourcemanager.webapp.address</name>
		<value>ssafy-e201:8188</value>
    </property>
    <!-- 스트리밍 처리를 위함 -->
     <property>
         <name>yarn.nodemanager.aux-services</name>
         <value>mapreduce_shuffle</value>
    </property>
    <property>
        <name>yarn.nodemanager.aux-services.mapreduce_shuffle.class</name>
        <value>org.apache.hadoop.mapred.ShuffleHandler</value>
    </property>
</configuration>
```



##### 3. HDFS 포맷 (최초 1회)

```bash
$ /eco/hadoop3/bin/hdfs namenode -format
```



##### 4. HDFS 시작

```bash
$ /eco/hadoop3/sbin/start-dfs.sh
```



#### Spark Cluster 설정

##### 1. 호스트 이름 설정

```bash
# hostname 변경
$ sudo hostnamectl set-hostname ssafy-e201

# hosts 파일 변경
$ sudo vi /etc/hosts
```

 

##### 2. ssh 등록

```bash
$ ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa

# authorized_keys 만들어서 본인 authorize 하기
$ cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys

# 권한 바꿈
$ chmod 600 ~/.ssh/authorized_keys
```



##### 3. Spark 설치

```bash
# spark 설치
$ wget https://archive.apache.org/dist/spark/spark-3.2.1/spark-3.2.1-bin-hadoop3.2.tgz

# tarball 압축 해제
$ tar xvfz spark-3.2.1-bin-hadoop3.2.tgz
```



##### 4. Spark 설정

> spark-env.sh

```bash
SPARK_MASTER_PORT=7177			# default: 7077
SPARK_MASTER_WEBUI_PORT=8180	# default: 8080
SPARK_WORKER_PORT=7178			# default: random
SPARK_WORKER_WEBUI_PORT=8181 	# default: 8081
SPARK_WORKER_CORES=8			# default: all available 일부러 뻥튀기
SPARK_WORKER_MEMORY=8G			# default: machine's total RAM minus GiB
SPARK_PUBLCI_DNS=${HOSTNAME}	
```



##### 5. Spark 실행

```bash
# Master와 Worker 모두 시작
$ /eco/spark3/sbin/start-all.sh
```



##### 6. Kafka Streaming consumer 작성

> /kafka/PlantId.py

```python
from pyspark.sql import SparkSession

if __name__=="__main__":
    spark = SparkSession.builder \
        .master("local") \
        .appName("save plantInfo") \
        .getOrCreate()\

    df = spark \
        .readStream \
        .format("kafka") \
        .option("kafka.bootstrap.servers", "localhost:9092") \
        .option("subscribe", "plantId") \
        .option("startingOffsets", "earlist") \
        .load()

    df.printSchema()

    # write - hdfs
    query = df.selectExpr("CAST(value AS STRING)") \
             .writeStream \
             .format("parquet") \
             .outputMode("append") \
             .option("checkpointLocation", "/check") \
             .option("path", "/user/dokcho/kafka/plantid") \
             .start()

    query.awaitTermination()
```

> /kafka/battles.py

```python
from pyspark.sql import SparkSession

if __name__=="__main__":
    spark = SparkSession.builder \
        .master("local") \
        .appName("save plantInfo") \
        .getOrCreate()\

    df = spark \
        .readStream \
        .format("kafka") \
        .option("kafka.bootstrap.servers", "localhost:9092") \
        .option("subscribe", "plantId") \
        .option("startingOffsets", "earlist") \
        .load()

    df.printSchema()

    # write - hdfs
    query = df.selectExpr("CAST(value AS STRING)") \
             .writeStream \
             .format("parquet") \
             .outputMode("append") \
             .option("checkpointLocation", "/check") \
             .option("path", "/user/dokcho/kafka/plantid") \
             .start()

    query.awaitTermination()
```



##### 7. Kafka Streaming consumer 실행

```sh
$ spark-submit --jars /eco/spark3/jars/spark-sql-kafka-0-10_2.13-3.2.2.jar ~/kafka/battles.py
$ spark-submit --jars /eco/spark3/jars/spark-sql-kafka-0-10_2.13-3.2.2.jar ~/kafka/battlelogs.py
$ spark-submit --jars /eco/spark3/jars/spark-sql-kafka-0-10_2.13-3.2.2.jar ~/kafka/plantId.py
```



#### Sqoop 설정

##### 1. Sqoop 설치

```bash
$ wget https://archive.apache.org/dist/sqoop/1.4.7/sqoop-1.4.7.tar.gz
$ tar xvfz sqoop-1.4.7.tar.gz
```

##### 2. mysql-connecter 설치

```bash
$ wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.30.tar.gz
# 압축 풀어서 /eco/sqoop/lib에 추가
```

##### 3. java-lang 설치

> backup.sh

```sh
$ wget https://dlcdn.apache.org//commons/lang/binaries/commons-lang-2.6-bin.tar.gz
# 압축풀어서 /eco/sqoop/lib에 추가
```



#### Kafka 설정

##### 1. docker-compose 설치

```bash
$ sudo apt install docker-compose
```



##### 2. docker-compose.yml 작성

> docker-compose.yml

```dockerfile
version: "3.3"
services:
        zookeeper:
                image: zookeeper:3.8
                container_name: zookeeper
                ports:
                        - "2181:2181"
                environment:
                        ZOO_MY_ID: 1

        broker1:
                image: confluentinc/cp-kafka:7.0.1
                container_name: broker1
                ports:
                        - "9092:9092"
                depends_on:
                        - zookeeper
                environment:
                        KAFKA_BROKER_ID: 1
                        KAFKA_ZOOKEEPER_CONNECT: 'zookeeper:2181'
                        KAFKA_ADVERTISED_LISTENERS: LISTENER_DOCKER_INTERNAL://broker1:29092,LISTENER_DOCKER_EXTERNAL://${DOKER_HOST_IP:-127.0.0.1}:9092
                        KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: LISTENER_DOCKER_INTERNAL:PLAINTEXT,LISTENER_DOCKER_EXTERNAL:PLAINTEXT
                        KAFKA_INTER_BROKER_LISTENER_NAME: LISTENER_DOCKER_INTERNAL
                        KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 3
                        KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
                        KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
                        KAFKA_CFG_MESSAGE_MAX_BYTES: 1000012000
                        KAFKA_MESSAGE_MAX_BYTES: 1000012000
                hostname: broker1

        broker2:
                image: confluentinc/cp-kafka:7.0.1
                container_name: broker2
                ports:
                        - "9093:9093"
                depends_on:
                        - zookeeper
                environment:
                        KAFKA_BROKER_ID: 2
                        KAFKA_ZOOKEEPER_CONNECT: 'zookeeper:2181'
                        KAFKA_ADVERTISED_LISTENERS: LISTENER_DOCKER_INTERNAL://broker2:29093,LISTENER_DOCKER_EXTERNAL://j7e201.p.ssafy.io:9093
                        KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: LISTENER_DOCKER_INTERNAL:PLAINTEXT,LISTENER_DOCKER_EXTERNAL:PLAINTEXT
                        KAFKA_INTER_BROKER_LISTENER_NAME: LISTENER_DOCKER_INTERNAL
                        KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 3
                        KAFKA_TRANSACTION_STATE_LOG_:MIN_ISR: 1
                        KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
                        KAFKA_CFG_MESSAGE_MAX_BYTES: 1000012000
                        KAFKA_MESSAGE_MAX_BYTES: 1000012000

        broker3:
                image: confluentinc/cp-kafka:7.0.1
                container_name: broker3
                ports:
                        - "9094:9094"
                depends_on:
                        - zookeeper
                environment:
                        KAFKA_BROKER_ID: 3
                        KAFKA_ZOOKEEPER_CONNECT: 'zookeeper:2181'
                        KAFKA_ADVERTISED_LISTENERS: LISTENER_DOCKER_INTERNAL://broker3:29094,LISTENER_DOCKER_EXTERNAL://${DOKER_HOST_IP:-127.0.0.1}:9094
                        KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: LISTENER_DOCKER_INTERNAL:PLAINTEXT,LISTENER_DOCKER_EXTERNAL:PLAINTEXT
                        KAFKA_INTER_BROKER_LISTENER_NAME: LISTENER_DOCKER_INTERNAL
                        KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 3
                        KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
                        KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
                        KAFKA_CFG_MESSAGE_MAX_BYTES: 1000012000
                        KAFKA_MESSAGE_MAX_BYTES: 1000012000


        kafdrop:
                image: obsidiandynamics/kafdrop
                restart: "no"
                ports:
                        - "9091:8080"
                environment:
                        KAFKA_BROKER_CONNECT: "broker1:29092"
                        SERVER_PORT: "8080"
                depends_on:
                        - zookeeper
                        - broker1
                        - broker2
                        - broker3
```



##### 3. docker-compose 실행

```bash
$ docker-compose -f docker-compose.yml up

webui = http://j7e201.p.ssafy.io:9091/
```



##### 4. path 설정

```sh
export PATH=/eco/sqoop/bin:$PATH
```



#### Cassandra 설정

##### 1. 카산드라 설치

```bash
$ wget https://archive.apache.org/dist/cassandra/4.0.5/apache-cassandra-4.0.5-bin.tar.gz
$ tar xvfz apache-cassandra-4.0.5-bin.tar.gz
```

##### 2. 카산드라 설정

> /eco/cassandra/conf/cassandra.yaml

```yaml
seeds: "{host}:7070"
listen_address: {hostname}
rpc_address: {hostname}
```

##### 3. CASSANDRA HOME, Path 등록

> .profile

```sh
export $CASSANDRA_HOME=/eco/cassandra
export PATH=/eco/cassandra/bin:$path
```

##### 4. Keyspace 생성

```cql
// local에서 사용할때는 replication_factor = 1
CREATE KEYSPACE dokcho WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };

CREATE TABLE imgdata ( 
    user_id int PRIMARY KEY, 
    img text, 
    genus text, 
    species text);
    
CREATE TABLE battle ( 
    user_id int PRIMARY KEY, 
    img text, 
    genus text, 
    species text);
```



#### AirFlow 설정

##### 1. pip 설치

```bash
$ sudo apt install pip
```

##### 2. airflow 설치

```bash
$ pip install flast-wtf==0.15
$ pip install WTForms==2.3.3
$ pip install Jinja2=3.1.2
$ pip install apache-airflow
```

##### 3. Metadata 초기화 및 사용자 추가

```bash
# Metadata 초기화
$ airflow db init

# 사용자 추가
$ airflow users create --username admin --password admin --firstname admin --lastname admin --role Admin --email admin@example.com
```

##### 4. sqoop.py

```python

from datetime import datetime
from airflow import DAG
from airflow.operators.bash import BashOperator
from airflow.operators.python import PythonOperator
from airflow.operators.empty import EmptyOperator

default_args = {
    'start_date': datetime(2022, 10, 4)
    }
with DAG(
    dag_id='sqoop_batch',
    schedule_interval='* */1 * * *',
    catchup=False,
    default_args=default_args
) as dag:
    run_bash1 = BashOperator(
        task_id='set_now',
        bash_command="now=$(date +'%y%m%d_%H')"
    )

    run_bash2 = BashOperator(
        task_id='backup_user',
        bash_command="/eco/sqoop/bin/sqoop import --connect jdbc:mysql://j7e201.p.ssafy.io:3306/dokcho --username root --password aoj29tj293092852tknwiog2395gw9023t --table users --target-dir /user/dokcho/backups/users/$now"
    )

    run_bash3 = BashOperator(
        task_id='backup_userMonster',
        bash_command="/eco/sqoop/bin/sqoop import --connect jdbc:mysql://j7e201.p.ssafy.io:3306/dokcho --username root --password aoj29tj293092852tknwiog2395gw9023t --table user_monster --target-dir /user/dokcho/backups/usermonster$now"
    )

    run_last = EmptyOperator(
        task_id="run_last"
    )

    run_bash1 >> [run_bash2, run_bash3] >> run_last
```







### Frontend

#### .env

```vue
VUE_APP_S3_URL={s3 url(독초몬 이미지)}
VUE_APP_PLANTS_S3_URL={s3 url(식물)}
```


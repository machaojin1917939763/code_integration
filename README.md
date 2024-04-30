# CodeIntegration 代码质量积分排名


## 代码开发日志
* DEVELOP-001（2024年4月8日）：初始化项目，构建开发环境，前期准备工作
* DEVELOP-002（2024年4月9日）：搭建GitLab Runner，每次提交代码通过GitLab Runner上传结果到SonarQube
* DEVELOP-002（2024年4月24日）：测试代码GitLab Runner是否能正常运行


## 开发过程重要代码
```shell
# 部署GitLba
docker run --detach --hostname gitlab.chaojin.com --publish 443:443 --publish 80:80 --publish 22:22 --name gitlab --restart always --volume D:\gitlab\config:/etc/gitlab --volume D:\gitlab\logs:/var/log/gitlab --volume D:\gitlab\data:/var/opt/gitlab gitlab/gitlab-ce:latest

# 创建网络
docker network create gs-network
# 将服务链接上docker网络
docker network connect gs-network gitlab
docker network connect gs-network sonarqube
docker network connect gs-network gitlab-runner

# 部署postgres数据库
docker run --name sonar-db --network gs-network -e POSTGRES_USER=sonar -e POSTGRES_PASSWORD=sonar -d postgres

# 部署sonarqube
docker run --name sonarqube --network gs-network -e SONAR_JDBC_URL=jdbc:postgresql://sonar-db:5432/sonar -e SONAR_JDBC_USERNAME=sonar -e SONAR_JDBC_PASSWORD=sonar -p 9000:9000 -v D:\sonarqube\data:/opt/sonarqube/data -v D:\sonarqube\extensions:/opt/sonarqube/extensions -v D:\sonarqube\logs:/opt/sonarqube/logs -v D:\sonarqube\temp:/opt/sonarqube/temp -d sonarqube

# 部署gitlab-runner
docker run -d --name gitlab-runner -p 8093:8093 --network gs-network --restart always -v D:\gitlab-runner\config:/etc/gitlab-runner -v D:\gitlab-runner\docker.sock:/var/run/docker.sock -v D:\gitlab-runner\docker-machine-config:/root/.docker/machine gitlab/gitlab-runner:latest

# 注册
docker run --rm -it -v D:\gitlab-runner\config:/etc/gitlab-runner gitlab/gitlab-runner register --non-interactive --url "http://172.21.0.2/" --registration-token "glrt-ELMyxyLtscUUQzZe9msW" --executor "docker" --docker-image "alpine:latest" --docker-network-mode "gs-network"
docker run --rm -it -v D:\gitlab-runner\config:/etc/gitlab-runner gitlab/gitlab-runner register

# 生成证书
New-SelfSignedCertificate -DnsName "gitlab.chaojin.com" -CertStoreLocation "D:\gitlab\cert"
# 分解ssl证书
openssl pkcs12 -in a.pfx -clcerts -nokeys -out certificate.crt
openssl pkcs12 -in a.pfx -nocerts -nodes -out privatekey.key
```
```
user = User.find_by(username: 'root')
user.password = 'Ma123456'
user.password_confirmation = 'Ma123456'
user.save!
```

```
mvn clean verify sonar:sonar -Dsonar.projectKey=root_codeintegration_32384cde-3930-428a-9154-46c38417dea5 -Dsonar.projectName='CodeIntegration' -Dsonar.host.url=http://localhost:9000/ -Dsonar.token=sqp_58c7c7ce4ece469c1460eb4082cfca59f80cc8cb
```

```
sonar-scanner.bat -D"sonar.projectKey=root_codeintegration_32384cde-3930-428a-9154-46c38417dea5" -D"sonar.sources=src/main/java/" -D"sonar.host.url=http://localhost:9000/" -D"sonar.token=sqp_58c7c7ce4ece469c1460eb4082cfca59f80cc8cb" -Dsonar.java.binaries=target/classes
```

```
mvn clean install sonar:sonar 
-Dsonar.projectKey=root_codeintegration_32384cde-3930-428a-9154-46c38417dea5
-Dsonar.host.url=http://localhost:9000
-Dsonar.token=sqp_58c7c7ce4ece469c1460eb4082cfca59f80cc8cb
-Dsonar.sources=src/main/java/
-Dsonar.java.binaries=target/classes
```

```
docker run -u root --rm -d -p 8080:8080 -p 50000:50000 -v jenkins-data:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock jenkinsci/blueocean
```

## sonar分析

```shell
mvn clean verify sonar:sonar \
  -DskipTests \
  -X \
  -Dsonar.projectKey=root_codeintegration_32384cde-3930-428a-9154-46c38417dea5 \
  -Dsonar.projectName='CodeIntegration' \
  -Dsonar.host.url=http://localhost:9000/ \
  -Dsonar.token=sqp_d5840812c84abae3a61ce39b2721d1d9b8d8eb14
```
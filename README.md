# paas-ta-container-platform-common-api

PaaS-TA에서 제공하는 컨테이너 플랫폼 관리에 필요한 메타데이터 제어를 위한 Database API를 제공합니다.

- [시작하기](#시작하기)
  - [컨테이너 플랫폼 COMMON API 빌드 방법](#컨테이너-플랫폼-COMMON-API-빌드-방법)
- [문서](#문서)
- [개발 환경](#개발-환경)
- [라이선스](#라이선스)

## 시작하기
PaaS-TA 컨테이너 플랫폼 COMMON API가 수행하는 관리 작업은 다음과 같습니다.

- 클러스터 정보 관리 API 제공
- 네임스페이스에 대한 ResourceQuotas, LimitRanges 구성 정책 API 제공
- 사용자 정보 관리 API 제공


## 컨테이너 플랫폼 COMMON API 빌드 방법
PaaS-TA 컨테이너 플랫폼 COMMON API 소스 코드를 활용하여 로컬 환경에서 빌드가 필요한 경우 다음 명령어를 입력합니다.
```
$ gradle build
```


## 문서
- 전체 컨테이너 플랫폼 관련 문서를 보려면 http://www.paas-ta.co.kr 을 참조하십시오.
- 컨테이너 플랫폼 활용에 대한 정보는 https://github.com/PaaS-TA/paas-ta-container-platform 의 README를 참조하십시오.


## 개발 환경
PaaS-TA 컨테이너 플랫폼 COMMON API의 개발 환경은 다음과 같습니다.

| Situation                      | Version |
| ------------------------------ | ------- |
| JDK                            | 8       |
| Gradle                         | 6.5     |
| Spring Boot                    | 2.3.3   |
| Spring Boot Data JPA           | 2.3.3   |
| Spring Boot Management         | 1.0.10  |
| MariaDB Java Client            | 2.2.6   |
| Lombok		                     | 1.18.12 |
| Jacoco		                     | 0.8.5   |
| Swagger	                       | 2.9.2   |


## 라이선스
[Apache-2.0 License](http://www.apache.org/licenses/LICENSE-2.0)

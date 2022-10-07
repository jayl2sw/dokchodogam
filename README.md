- # 독초도감
  
  `캐치프레이즈 넣는 곳`
  
  `지현이 만들어줄 커버 이미지`

  `독초도감 소개`

  `독초도감 링크`

## 목차

- [독초도감](#독초도감)
  
  - [목차](#목차)
  - [서비스 소개](#서비스-소개)
    - [📋 기술 스택 및 버전](#기술-스택-및-버전)
  - [프로젝트 파일 구조](#프로젝트-파일-구조)
  - [산출물](#산출물)
  - [결과물](#결과물)

- [📺 프로젝트 UCC](https://drive.google.com/file/d/1AMcAO6QFbD6dapB6_n6oYhm9_pHbsGKm/view?usp=sharing)

## 서비스 소개

1. 개발 기간 : 2022.08.22 ~ 2022.10.07 (총 6주)
2. 인원 (총 6인)
   - 서상균 : 팀장, Front-end, https://github.com/seosanggyun
     - 김성빈 : Back-end, DevOps, https://github.com/ksb0903
     - 박지현 : Front-end, UI/UX, https://github.com/petteloiv
     - 오하민 : Front-end, PWA, https://github.com/ohhamin
     - 이재준 : Back-end, DevOps, https://github.com/jayl2sw
     - 최지원 : Back-end, UI/UX, https://github.com/CHOIJXWXN

## 기술 스택 및 버전

1. 이슈 관리 : ![Jira](https://img.shields.io/badge/jira-%230A0FFF.svg?style=for-the-badge&logo=jira&logoColor=white)
2. 형상 관리: ![GitLab](https://img.shields.io/badge/gitlab-%23181717.svg?style=for-the-badge&logo=gitlab&logoColor=white)
3. 커뮤니케이션: ![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white) ![Discord](https://img.shields.io/badge/discord-5865F2.svg?style=for-the-badge&logo=discord&logoColor=white) 
4. 개발 환경 
   - IDE : ![IntelliJ](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)2022.1.2 ![Visual_Studio_Code](https://img.shields.io/badge/Visual%20Studio%20Code-0078d7.svg?style=for-the-badge&logo=visual-studio-code&logoColor=white)1.71.2
   - DB : ![MySQL](https://img.shields.io/badge/MySQL-003545?style=for-the-badge&logo=mysql&logoColor=white) 8.0.30 ![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white) 6.0.1 
   - UI & UX : ![Figma](https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white) 
   - Server : ![AWS](https://img.shields.io/badge/AWS-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white) ![S3](https://img.shields.io/badge/S3-569A31?style=for-the-badge&logo=amazons3&logoColor=white) ![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=white) ![ubuntu](https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white)![NGINX](https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=white) ???
5. 상세
   - Backend : ![JAVA](https://img.shields.io/badge/JAVA-FF0000?style=for-the-badge&logoColor=white) 1.8 ![Spring](https://img.shields.io/badge/Spring boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)2.7.3 ![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)7.5 ![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)0.11.2
   - Frontend : ![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)![CSS3](https://img.shields.io/badge/css3-1572B6?style=for-the-badge&logo=css3&logoColor=white)![JAVASCRIPT](https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white)![Vue.js](https://img.shields.io/badge/vue.js-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white) 3.2.39 ![Node.js](https://img.shields.io/badge/Node.js-339933?style=for-the-badge&logo=node.js&logoColor=white)16.16.0 ![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=type&logoColor=white) 4.5.5 ![bootstrap](https://img.shields.io/badge/BootStrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white) 5.2.1
   - CI/CD : ![docker](https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)20.10.18 ![Vue.js](https://img.shields.io/badge/jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white) 2.222.4
   - Data : ![python](https://img.shields.io/badge/python-3776AB?style=for-the-badge&logo=python&logoColor=white)3.8.10 ![hadoop](https://img.shields.io/badge/hadoop-66CCFF?style=for-the-badge&logo=apache hadoop&logoColor=white)3.3.2 ![spark](https://img.shields.io/badge/spark-E25A1C?style=for-the-badge&logo=apache spark&logoColor=white) 3.2.1 ![kafka](https://img.shields.io/badge/kafka-231F20?style=for-the-badge&logo=apache kafka&logoColor=white) 7.0.1  

## 프로젝트 파일 구조

- Backend
  
  ```
  main
  ├─generated
  ├─java
  │  └─com
  │      └─ssafy
  │          └─dockchodogam
  │             ├─config
  │             ├─controller
  │             ├─domain
  │             │  ├─basetime
  │             │  └─enums
  │             ├─dto
  │             │  ├─battle
  │             │  ├─exception
  │             │  │  ├─common
  │             │  │  ├─game
  │             │  │  ├─plant
  │             │  │  └─user
  │             │  ├─game
  │             │  ├─gg
  │             │  ├─jwt
  │             │  ├─oauth2
  │             │  ├─plant
  │             │  └─user
  │             ├─jwt
  │             ├─kafka
  │             ├─repository
  │             ├─service
  │             │  ├─admin
  │             │  ├─battle
  │             │  ├─dokcho
  │             │  ├─game
  │             │  ├─jwt
  │             │  ├─oauth2
  │             │  └─user
  │             └─util
  └─resources
  ```

- Frontend **이거 하민이 해**
  
  ```
  rc
  ├─assets
  │  ├─arena
  │  ├─intro
  │  ├─intro-card
  │  ├─loading
  │  ├─login
  │  ├─monster
  │  └─shop
  ├─components
  │  ├─admin
  │  ├─camera
  │  ├─encyclopedia
  │  ├─game
  │  │  ├─arena
  │  │  ├─friend
  │  │  ├─gg
  │  │  └─shop
  │  ├─main
  │  ├─mypage
  │  ├─search
  │  └─start
  ├─constant
  ├─router
  ├─store
  │  └─modules
  └─components
     ├─admin
     ├─camera
     ├─encyclopedia
     ├─error
     ├─gallery
     ├─game
     ├─main
     ├─mypage
     ├─search
     └─start
  
  ```
  
  <br><br>
  
  ## 산출물

- [프로젝트 메모 및 공유 : Notion](https://www.notion.so/dokcho/bee30bec2cdb4479a7db031c9d7806e8)

- [프로젝트 회의록](https://www.notion.so/dokcho/84b2d7b282774267a12c866524a1f481?v=2456f84e544d459eb873931dd94d261b)
  
  <br>

- [프로젝트 컨벤션 목록](https://www.notion.so/dokcho/73506d36df0844f2b1e13e61a4a2aae3)

- [기획서](https://www.notion.so/dokcho/92608bad2eaf4831b3c893ff731baa22)

- [API Docs](https://www.notion.so/dokcho/API-Docs-8c38c792438a439f88a7dafdbf77a25f)

- [와이어 프레임](./docs/와이어프레임.md) (아직 링크 안 걸었음)

- [서비스 아키텍처](./docs/아키텍처.md) (아직 링크 안 걸었음)

- [기능 명세서](https://www.notion.so/dokcho/API-Docs-8c38c792438a439f88a7dafdbf77a25f)

- [데이터베이스:ERD](./docs/ERD.md) (아직 링크 안 걸었음)
  
  <br><br>
  
  ## 결과물 (얘네들 전부 나중에 링크 달아야함)

- [포팅메뉴얼](./exec/포팅메뉴얼.md)

- [시연시나리오](./exec/시연시나리오.md)

- [중간발표자료](./pt/[중간발표]특화_PJT_구미1반_D104.pdf)

- [최종발표자료](./pt/[최종발표]특화_PJT_구미1반_D104.pdf)

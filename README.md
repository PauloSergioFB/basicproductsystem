# Basic Products System - CP05 DevOps

![Java 21](https://img.shields.io/badge/Java%2021-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![FIAP](https://img.shields.io/badge/FIAP-ED145B?style=for-the-badge&logoColor=white)

---

## Setup do Projeto

Antes de iniciar, certifique-se de ter instalado:

- **Git**
- **Java** (versão 21)
- **Maven** (mvn)

#### 1. Clonar Repositório
```bash
# Clonar o repositório
git clone https://github.com/PauloSergioFB/basicproductsystem

# Acessar o diretório
cd basicproductsystem
```

#### 2. Iniciar o projeto

```bash
mvn spring-boot:run
```

Após a inicialização, a API estará disponível em: http://localhost:8080  

## Instruções para Deploy

Antes de iniciar, certifique-se de ter instalado:

- **Azure CLI autenticado no terminal**  
- **Permissão para criar os recursos necessários através do CLI**

### 1. Dar permissão de execução ao script

```bash
chmod +x deploy.sh
```

### 2. Executar o script

```bash
./deploy.sh
```

### 3. Autorizar o GitHub

Durante a execução, será solicitado login no GitHub. Siga as instruções que aparecem no terminal.

### 4. Primeiro deploy (irá falhar)

Após a execução do script o primeiro deploy irá falhar propositalmente, pois ainda não configuramos os **secrets do GitHub**.

### 5. Criar GitHub Secrets

Vá em Settings → Secrets and variables → Actions e adicione as variáveis de ambiente clicando em: New repository secret

- DB_URL = <sua_db_url>  
- DB_USERNAME = <seu_db_user>    
- DB_PASSWORD = <sua_db_password>    

### 6. Ajustar o workflow (.yml)

Agora vamos configurar o projeto para buscar as variáveis dentro do github secrets.  
**OBS:** Se atente a indentação do arquivo

Antes
```yaml
- name: Build with Maven
      run: mvn clean install
```

Depois
Antes
```yaml
- name: Build with Maven
      run: mvn clean install
      env:
        DB_URL: ${{ secrets.DB_URL }}
        DB_USERNAME: ${{ secrets.DB_USERNAME }}
        DB_PASSWORD: ${{ secrets.DB_PASSWORD }}
```

### 8. Commit da alteração

```bash
git add .
git commit -m "ci: update workflow to use db secrets"
git push origin main
```

## Integrantes do Grupo

[@AntonioDeLuca](https://github.com/antoniodeluca)  
[@Paulo Sérgio França Barbosa](https://github.com/PauloSergioFB)  

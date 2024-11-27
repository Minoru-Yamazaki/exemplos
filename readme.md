# Análise de Código com SonarQube

## Descrição

Esta iniciativa faz parte de um OKR da Muralis para trazer visibilidade e métricas de qualidade dos projetos desenvolvidos ou mantidos pela Muralis.

O SonarQube é uma plataforma de código aberto que permite a análise contínua da qualidade do código, identificando bugs, vulnerabilidades e problemas de manutenção, além de fornecer informações sobre a cobertura de testes.

## Inicializar o Container

Para iniciar o SonarQube, execute o seguinte comando para iniciar os serviços em segundo plano:
  

```BASH
docker-compose up -d
```

Isso iniciará os serviços em segundo plano. O SonarQube estará disponível em http://localhost:9000 e pode demorar alguns segundos para estar totalmente operacional.

## Configurar o Projeto no SonarQube

1. Acesse http://localhost:9000 e faça login com as credenciais padrão: admin/admin.
  
2. No painel, crie um novo projeto para obter o project key (Por padrão, é o nome do projeto)).
  

## Obter o Token

1. Clique no ícone do usuário administrador no canto superior direito (normalmente uma letra "A").
  
2. Navegue até My Account > Security.
  
3. Gere um token do tipo Global Analysis Token.
  

## Configurar o SonarScanner

1. **Instale o SonarScanner**:
  
Baixe o SonarScanner a partir do site oficial do [SonarQube](https://docs.sonarsource.com/sonarqube/latest/analyzing-source-code/scanners/sonarscanner/) e extraia o arquivo baixado em um diretório de sua escolha.

2. **Adicione o SonarScanner ao PATH**:
  
  Para poder executar o `sonar-scanner` de qualquer lugar, adicione o caminho da pasta `bin` do SonarScanner ao `PATH` do sistema:
  
  - **Windows:**
  
    1. No menu Iniciar, digite **"variáveis de ambiente"** e selecione **Editar as variáveis de ambiente do sistema**.
        
    2. Na janela de Propriedades do Sistema, clique em **Variáveis de Ambiente**.
        
    3. Em **Variáveis do Sistema**, encontre a variável `Path` e clique em **Editar**.
        
    4. Clique em **Novo** e adicione o caminho completo para a pasta `bin` do SonarScanner, por exemplo:
        
        plaintext
        
        Copiar código
        
        `C:\sonar-scanner\bin`
        
    5. Clique em **OK** em todas as janelas para confirmar as alterações.

  - **Linux:**

    _todo_

  - **Mac:**

    _todo_
    
3. **Verifique a Instalação**:
  
Abra um terminal e execute o seguinte comando para verificar se o SonarScanner está instalado corretamente:

```bash
sonar-scanner --version
```
  

## Configurar o Script

Dentro deste projeto, há uma pasta para cada linguagem. Navegue até a pasta da sua linguagem e siga estas etapas:

1. Configure o seu projeto com base no README.md correspondente.
  
2. Atualize as credenciais no seu script .bat ou .sh nas variáveis SONAR_TOKEN e SONAR_KEY.
  
3. Execute o script apropriado (sonar.bat ou sonar.sh) do seu projeto.
  

### Estrutura do Projeto

- docker-compose.yml: Arquivo de configuração para iniciar os containers do SonarQube e PostgreSQL.
  
- java/: Configurações e scripts específicos para projetos Java.
  
- nodejs/: Configurações e scripts específicos para projetos Node.js.
  
- python/: Configurações e scripts específicos para projetos Python.
  
- dotnet/: Configurações e scripts específicos para projetos .NET.
  

## Observações

- Certifique-se de que o Docker e o Docker Compose estão instalados e configurados corretamente em sua máquina.
  
- Assegure-se de que as portas necessárias (como 9000 para o SonarQube) não estão sendo utilizadas por outros serviços.
@echo off

:: Navega até o diretório do projeto
echo Setting environment variables...
set SONAR_HOST_URL=http://localhost:9000
set SONAR_TOKEN=sqa_6943b230147329b1b2bc915e0e768582304d6dd2
set PROJECT_KEY=teste
set PROJECT_DIR=C:\Users\minor\OneDrive\Documentos\GitHub\exemplos

:: Navega até o diretório do projeto
echo Navigating to project directory: %PROJECT_DIR%
cd /d "%PROJECT_DIR%"

:: Executa a análise com o SonarScanner
echo Running SonarScanner for project %PROJECT_KEY%...
sonar-scanner ^
  "-Dsonar.projectKey"=%PROJECT_KEY% ^
  "-Dsonar.sources"=src/main/java ^
  "-Dsonar.host.url"=%SONAR_HOST_URL% ^
  "-Dsonar.token"=%SONAR_TOKEN% ^
  "-Dsonar.coverage.jacoco.xmlReportPaths"=target/site/jacoco/jacoco.xml ^
  "-Dsonar.java.binaries"=target/classes

  echo Sonar analysis completed.
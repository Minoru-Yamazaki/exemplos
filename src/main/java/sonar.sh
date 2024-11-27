#!/bin/bash

# Configuração das variáveis de ambiente
echo "Setting environment variables..."
SONAR_HOST_URL="http://localhost:9000"
SONAR_TOKEN="sqa_6943b230147329b1b2bc915e0e768582304d6dd2"
PROJECT_KEY="sqa_6943b230147329b1b2bc915e0e768582304d6dd2"
PROJECT_DIR=""

# Navega até o diretório do projeto
echo "Navigating to project directory: $PROJECT_DIR"
cd "$PROJECT_DIR" || { echo "Directory not found: $PROJECT_DIR"; exit 1; }

# Executa a análise com o SonarScanner
echo "Running SonarScanner for project $PROJECT_KEY..."
sonar-scanner \
  -Dsonar.projectKey="$PROJECT_KEY" \
  -Dsonar.sources=src/main/java \
  -Dsonar.host.url="$SONAR_HOST_URL" \
  -Dsonar.token="$SONAR_TOKEN" \
  -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
  -Dsonar.java.binaries=target/classes

echo "Sonar analysis completed."
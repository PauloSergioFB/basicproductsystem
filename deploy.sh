# Variáveis
RESOURCE_GROUP_NAME="rg-basicproductssytem-deploy"
WEBAPP_NAME="basicproductssytem"
APP_SERVICE_PLAN="basicproductssytem"
LOCATION="brazilsouth"
RUNTIME="JAVA:21-java21"
GITHUB_REPO_NAME="PauloSergioFB/basicproductsystem"
BRANCH="main"
APP_INSIGHTS_NAME="ai-basicproductssytem"

# Dados do banco
SPRING_DATASOURCE_USERNAME_VALUE=
SPRING_DATASOURCE_PASSWORD_VALUE=
SPRING_DATASOURCE_URL_VALUE=

# Criar Grupo de Recursos
az group create \
  --name $RESOURCE_GROUP_NAME \
  --location "$LOCATION"

# Criar Application Insights
az monitor app-insights component create \
  --app $APP_INSIGHTS_NAME \
  --location "$LOCATION" \
  --resource-group $RESOURCE_GROUP_NAME \
  --application-type web

# Criar Plano de Serviço
az appservice plan create \
  --name $APP_SERVICE_PLAN \
  --resource-group $RESOURCE_GROUP_NAME \
  --location "$LOCATION" \
  --sku F1 \
  --is-linux

# Criar Serviço de Aplicativo
az webapp create \
  --name $WEBAPP_NAME \
  --resource-group $RESOURCE_GROUP_NAME \
  --plan $APP_SERVICE_PLAN \
  --runtime "$RUNTIME"

# Listar runtimes disponíveis
az webapp list-runtimes

# Habilita a autenticação Básica (SCM)
az resource update \
  --resource-group $RESOURCE_GROUP_NAME \
  --namespace Microsoft.Web \
  --resource-type basicPublishingCredentialsPolicies \
  --name scm \
  --parent sites/$WEBAPP_NAME \
  --set properties.allow=true

# Recuperar a String de Conexão do Application Insights
CONNECTION_STRING=$(az monitor app-insights component show \
  --app $APP_INSIGHTS_NAME \
  --resource-group $RESOURCE_GROUP_NAME \
  --query connectionString \
  --output tsv)

# Configurar as Variáveis de Ambiente necessárias do App e do Application Insights
az webapp config appsettings set \
  --name "$WEBAPP_NAME" \
  --resource-group "$RESOURCE_GROUP_NAME" \
  --settings \
    APPLICATIONINSIGHTS_CONNECTION_STRING="$CONNECTION_STRING" \
    ApplicationInsightsAgent_EXTENSION_VERSION="~3" \
    XDT_MicrosoftApplicationInsights_Mode="Recommended" \
    XDT_MicrosoftApplicationInsights_PreemptSdk="1" \
    SPRING_DATASOURCE_USERNAME="$SPRING_DATASOURCE_USERNAME_VALUE" \
    SPRING_DATASOURCE_PASSWORD="$SPRING_DATASOURCE_PASSWORD_VALUE" \
    SPRING_DATASOURCE_URL="$SPRING_DATASOURCE_URL_VALUE"

# Reiniciar o Web App
az webapp restart \
  --name $WEBAPP_NAME \
  --resource-group $RESOURCE_GROUP_NAME

# Criar a conexão do Web App com o Application Insights
az monitor app-insights component connect-webapp \
  --app $APP_INSIGHTS_NAME \
  --web-app $WEBAPP_NAME \
  --resource-group $RESOURCE_GROUP_NAME

# Configurar GitHub Actions para Build e Deploy automático
az webapp deployment github-actions add \
  --name $WEBAPP_NAME \
  --resource-group $RESOURCE_GROUP_NAME \
  --repo $GITHUB_REPO_NAME \
  --branch $BRANCH \
  --login-with-github

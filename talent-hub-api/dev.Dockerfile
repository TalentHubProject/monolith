FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build

COPY . /app

WORKDIR /app

RUN dotnet restore

RUN dotnet build -c Debug

ENV ASPNETCORE_ENVIRONMENT=Development
ENV ASPNETCORE_URLS=http://*:5106

CMD ["dotnet", "watch", "run", "--project", "TalentHubDiscordApi/TalentHubDiscordApi.csproj", "--urls=http://0.0.0.0:5106"]

[![GitHub License](https://img.shields.io/github/license/TalentHubProject/monolith)](https://github.com/TalentHubProject/monolith/blob/main/LICENSE)
[![GitHub Issues](https://img.shields.io/github/issues/TalentHubProject/monolith)](https://github.com/TalentHubProject/monolith/issues)
[![GitHub Last Commit](https://img.shields.io/github/last-commit/TalentHubProject/monolith)](https://github.com/TalentHubProject/monolith/commits/main)

![monolith](https://socialify.git.ci/TalentHubProject/monolith/image?description=1&descriptionEditable=An%20integrated%20monolithic%20solution%20for%20managing%20interactions%20and%20data%20within%20Talent%20Hub&font=Inter&forks=1&issues=1&language=1&logo=https%3A%2F%2Favatars.githubusercontent.com%2Fu%2F124662485%3Fs%3D200%26v%3D4&name=1&owner=1&pattern=Plus&pulls=1&stargazers=1&theme=Auto)

[Discord](https://discord.talent-hub.fr) | [Website](https://talent-hub.fr)

## Prerequisites

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Installation
1. **Clone the Repository**

```bash
git clone https://github.com/TalentHubProject/monolith.git
cd monolith
```

2. **Environment Configuration**

Duplicate the .env.example file to .env and adjust the environment variables as needed.

```bash
cp .env.example .env
```

3. **Launching Services**

Use Docker Compose to build and start the services:

<details>
<summary><strong>Production</strong></summary>

```bash
docker-compose up -d
```

</details>

<details>
<summary><strong>Development</strong></summary>

```bash
docker-compose -f docker-compose.dev.yml up -d
```

</details>

## Services

| Service     | Port | Technology | Description                                     |
|-------------|------|------------|-------------------------------------------------|
| API         | 5000 | Asp.NET    | REST API for managing Talent Hub data           |
| Discord Bot | 5001 | JDA        | The famous Aleks Bot ! ꒰(˶• ᴗ •˶)꒱              |
| Website     | 3000 | NuxtJs     | Web interface for accessing Talent Hub features |
| Database    | 5432 | PostgreSQL | Relational database management system           |


## Docker Network

- **Name**: `th-dev_net`
- **Driver**: `bridge`

## Contributing

We encourage contributions to the Talent Hub Monolith project! Please see [CONTRIBUTING.md](.github/CONTRIBUTING.md) for more information on how to contribute.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Contributors
<a href="https://github.com/talenthubproject/monolith/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=talenthubproject/monolith" />
</a>

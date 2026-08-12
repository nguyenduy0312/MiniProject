# Employee Management System

## Spring Profiles

The default active profile is `dev`, configured in `src/main/resources/application.yml`.

### Run with dev profile

The dev database configuration is in `src/main/resources/application-dev.yml`.

```bash
mvn spring-boot:run
```

or explicitly:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Dev uses the local MySQL database:

```text
jdbc:mysql://localhost:3306/employee_management
```

### Run with prod profile

The prod database configuration is in `src/main/resources/application-prod.yml`.
Production database values must be provided through environment variables.

PowerShell:

```powershell
$env:DB_URL="jdbc:mysql://prod-host:3306/employee_management"
$env:DB_USERNAME="your_prod_username"
$env:DB_PASSWORD="your_prod_password"
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

Command Prompt:

```cmd
set DB_URL=jdbc:mysql://prod-host:3306/employee_management
set DB_USERNAME=your_prod_username
set DB_PASSWORD=your_prod_password
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

Do not commit real production database passwords to Git.

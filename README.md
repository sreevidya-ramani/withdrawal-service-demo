# withdrawal-service

Java + Spring Boot microservice that accepts retirement withdrawal requests and checks them with the Eligibility Service over REST.

## API

`POST /api/v1/withdrawals` (default port `8082`)

Request fields:

```json
{
	"participantId": "P1001",
	"withdrawalType": "PARTIAL",
	"amount": 500.00,
	"status": "PENDING"
}
```

The service sends `participantId`, `withdrawalType`, and `amount` to the Eligibility Service at `eligibility.base-url` (default `http://localhost:8080`). The request's `status` field is not used in the eligibility check.

Responses have `status` and `message` fields. An eligible request returns `APPROVED` with `Withdrawal submitted`. A refused request returns `REJECTED` with `Withdrawal not allowed`; the eligibility reason is currently not forwarded.

The service does not save or record withdrawals; it returns the outcome of the eligibility check.

## Run and test

From this repository's `withdrawal-service` directory:

```sh
./mvnw spring-boot:run
./mvnw test
```

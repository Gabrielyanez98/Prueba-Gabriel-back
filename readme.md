# Backend dev technical test
We want to offer a new feature to our customers showing similar products to the one they are currently seeing. To do this we agreed with our front-end applications to create a new REST API operation that will provide them the product detail of the similar products for a given one. [Here](./similarProducts.yaml) is the contract we agreed.

We already have an endpoint that provides the product Ids similar for a given one. We also have another endpoint that returns the product detail by product Id. [Here](./existingApis.yaml) is the documentation of the existing APIs.

**Create a Spring boot application that exposes the agreed REST API on port 5000.**

![Diagram](./assets/diagram.jpg "Diagram")

Note that _Test_ and _Mocks_ components are given, you must only implement _yourApp_.

## Testing and Self-evaluation
You can run the same test we will put through your application. You just need to have docker installed.

First of all, you may need to enable file sharing for the `shared` folder on your docker dashboard -> settings -> resources -> file sharing.

Then you can start the mocks and other needed infrastructure with the following command.
```
docker-compose up -d simulado influxdb grafana
```
Check that mocks are working with a sample request to [http://localhost:3001/product/1/similarids](http://localhost:3001/product/1/similarids).

To execute the test run:
```
docker-compose run --rm k6 run scripts/test.js
```
Browse [http://localhost:3000/d/Le2Ku9NMk/k6-performance-test](http://localhost:3000/d/Le2Ku9NMk/k6-performance-test) to view the results.

## Evaluation
The following topics will be considered:
- Code clarity and maintainability
- Performance
- Resilience

## Running the Application

### Prerequisites
- Docker and Docker Compose installed


### Starting the Application

1. **Start all services** (application, mocks, and monitoring infrastructure):
   ```bash
   docker-compose up -d --build
   ```

2. **Verify the services are running**:
   ```bash
   docker-compose ps
   ```

### Testing the Application

#### Manual Testing

The application exposes the Similar Products API on port **5000**.

**Test with a valid product:**
```bash
curl http://localhost:5000/product/1/similar
```

Expected response:
```json
[
  {"id":"2","name":"Dress","price":19.99,"availability":true},
  {"id":"3","name":"Blazer","price":29.99,"availability":false},
  {"id":"4","name":"Boots","price":39.99,"availability":true}
]
```

**Test with a non-existent product:**
```bash
curl http://localhost:5000/product/9999/similar
```

Expected response (404):
```json
{
  "message":"Product not found: 9999",
  "timestamp":"2026-02-04T18:38:21.794",
  "status":404
}
```

### Stopping the Application

```bash
docker-compose down
```

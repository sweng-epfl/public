from locust import HttpUser, task


class User(HttpUser):
    @task
    def loop(self):
        self.client.get("/loop")
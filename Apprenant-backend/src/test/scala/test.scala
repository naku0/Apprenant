class test extends Http4sDsl[IO]{
  val routes = HttpRoutes.of[IO] {
    case GET -> Root / "test" =>
      Ok("Hello from Coding Platform!")
  }
}

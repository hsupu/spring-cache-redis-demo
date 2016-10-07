## demo: Spring Cache with Redis

A Maven Project.

There is a demo showing how to use Spring Cache Annotation (`spring-context`) and Redis Server (`spring-data-redis`).

Implemented Serializer:

- StringRedisSerializer
- GenericToStringRedisSerializer
- JdkSerializationRedisSerializer
- KryoRedisSerializer (with KryoPool)
- GenericJackson2JsonRedisSerializer
- NumberRedisSerializer (Customized)

We're recommended to use `JdkSerializationRedisSerializer` instead of `GenericJackson2JsonRedisSerializer` because Jackson is unfriendly to Strongly-Typed Number.
Jackson will not convert type automatically between `Integer` and `Long`, only by configuring `USE_LONG_FOR_INTS` with `ObjectMapper` can we use `Long` instead of `Integer` when the value in the range of `Integer`.

[Kryo](https://github.com/EsotericSoftware/kryo/) is a fast and efficient object graph serialization framework for Java.

During development, you may run `flushdb` in `redis-cli` firstly and try again when catch a Exception.

Other features you can learn from this repository:

- Logging: Logback over slf4j
- Unit Test: JUnit with Spring
- Externalized Configuration
  - Yaml Support
  - Properties Injection

Well.. my pool English, and welcome to star.
# PicoContainer support for vert.x to use dependency injection.

## Usage

Create a Verticle:

    public class MyVerticle extends Verticle {

        public void start() {

            ApplicationContext applicationContext = ApplicationContext.create()
                    .withContainer(container)
                    .withVertx(vertx)
                    .withClass(OtherService.class)
                    .withClass(MyService.class)
                    .build();

            MyService myService = applicationContext.getComponent(MyService.class);
            myService.start();

            container.logger().info("MyService started");

        }
    }

The service class is able to use dependency injection based on PicoContainer:

    public class MyService {

        @Inject
        private Logger logger;

        @Inject
        private Vertx vertx;

        @Inject
        private OtherService otherService;

        public void start() {
        }
    }
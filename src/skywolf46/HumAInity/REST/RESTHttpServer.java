package skywolf46.HumAInity.REST;


import static spark.Spark.*;
public class RESTHttpServer {
    // http://www.wildml.com/2016/04/deep-learning-for-chatbots-part-1-introduction/
    private static RESTHttpServer server = null;

    private RESTHttpServer(){
        post("/test",(req,resp) -> {

            return "";
        });
    }
    public static void startRESTServer() {
        if (server != null)
            throw new IllegalStateException("REST Http Server already started");
        server = new RESTHttpServer();
    }


}

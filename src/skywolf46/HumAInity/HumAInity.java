package skywolf46.HumAInity;

import dev.jeka.core.api.depmanagement.JkDependencyResolver;
import dev.jeka.core.api.depmanagement.JkDependencySet;
import dev.jeka.core.api.depmanagement.JkJavaDepScopes;
import dev.jeka.core.api.depmanagement.JkRepo;
import org.xeustechnologies.jcl.JarClassLoader;
import skywolf46.HumAInity.PacketConnection.Encryption.Extension.RSA.Util.RSAKeypairGenerator;
import skywolf46.HumAInity.PacketConnection.PacketConnection;
import skywolf46.HumAInity.PacketConnection.Test.TestPingPacket;
import skywolf46.HumAInity.Server.Data.DataServer;
import skywolf46.HumAInity.Server.Login.LoginServer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

// 봇 학습은 단어를 연관시킴으로 학습시킨다.
// 봇에게 학습시킨 연관 단어가 많을수록, 봇은 더 높은 정확도를 가지게 된다.
public class HumAInity {
    // Starting
    public static void main(String[] args) {
        try {
            File data = new File("Libs");
            if (!data.exists()) {
                JkDependencySet dep = JkDependencySet.of()
                        .and("org.slf4j:slf4j-simple:1.6.4")
                        .and("org.slf4j:slf4j-api:1.7.25")
                        .and("org.bytedeco:javacpp:1.5.1-1")
                        .and("org.nd4j:nd4j-native-platform:1.0.0-beta4")
                        .and("org.deeplearning4j:deeplearning4j-core:1.0.0-beta5")
                        .and("org.deeplearning4j:deeplearning4j-nlp:1.0.0:beta5")
                        .and("org.bouncycastle:bcprov-jdk15on:1.64")
                        .and("org.minidns:minidns-hla:0.3.4")
                        .and("org.xerial:sqlite-jdbc:3.28.0")
                        .and("com.sparkjava:spark-core:2.8.0")
                        .withDefaultScopes(JkJavaDepScopes.RUNTIME);
                System.out.println("Resolving dependency...");
                JkDependencyResolver resv = JkDependencyResolver.of(JkRepo.ofMavenCentral());
                List<Path> libs = resv.resolve(dep, JkJavaDepScopes.RUNTIME).getFiles().getEntries();
                System.out.println("Copy...");
                for (Path p : libs) {
                    File target = new File(data, p.getFileName().toString());
                    target.getParentFile().mkdirs();
                    if (target.exists())
                        target.delete();
//                    target.createNewFile();
                    Files.copy(p, target.toPath());
                }
                System.out.println("Resolved!");

            }
            HashSet<File> errandreload = new HashSet<>();
            errandreload.addAll(Arrays.asList(data.listFiles()));
            System.out.println("Loading jars...");
            int reload = 0;
            while (errandreload.size() > 0) {
                if (reload > 5) {
                    System.out.println("Cannot resolve dependency. Unloaded Dependency: ");
                    for (File f : errandreload)
                        System.out.println(f.getName());
                    System.exit(0);
                }
                if (reload > 0)
                    System.out.println("Dependency error. Re-loading " + errandreload.size() + " files...(Try " + reload + ")");
                List<File> newList = new ArrayList<>(errandreload);
                errandreload.clear();
                for (File p : newList) {
                    addToClasspath(p);
//                    JarFile jarFile = new JarFile(p);
//                    Enumeration<JarEntry> e = jarFile.entries();
//
//                    URL[] urls = {p.toPath().toUri().toURL()};
//                    URLClassLoader cl = URLClassLoader.newInstance(urls, HumAInity.class.getClassLoader());
//
//                    while (e.hasMoreElements()) {
//                        JarEntry je = e.nextElement();
//                        if (je.isDirectory() || !je.getName().endsWith(".class")) {
//                            continue;
//                        }
//                        // -6 because of .class
//                        String className = je.getName().substring(0, je.getName().length() - 6);
//                        className = className.replace('/', '.');
//                        try {
//                            Class c = cl.loadClass(className);
////                        System.out.println("Loaded " + c.getName());
//                        } catch (ClassNotFoundException | NoClassDefFoundError exx) {
//                            errandreload.add(p);
//                        } catch (Exception ex) {
////                            ex.printStackTrace();
//                        } catch (Error er) {
////                            er.printStackTrace();
//                        }
//                    }
//                }
                    reload++;
                }
                System.out.println("Completed! Starting server...");
                LoginServer.loadData("userDB.db");
                new TestPingPacket("").register();
//                PacketConnection.attachPacketListener(10000,(con,packet) -> {
//                    TestPingPacket ping = (TestPingPacket) packet;
//                    System.out.println("Ping packet recieved : " + ping.getPing());
//                });
                new LoginServer(9817, RSAKeypairGenerator.generateKeypair(1024)).startServer();
                new DataServer(9818, RSAKeypairGenerator.generateKeypair(1024)).start();
                while (true) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void addToClasspath(File file) {
        try {
            URL url = file.toURI().toURL();

            URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(classLoader, url);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }

}
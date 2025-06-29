Welcome to AI Recommendation system

(Preferred terminal git bash & os windows)
Insturctions to follow(must) & Internet connection required

 1. Create required directories using following commands 
    ```mkdir recommendation-system```
    ```cd recommendation-system```
    ```mkdir lib```

 2. Install Apache mahout in your system by following command

    ```curl -O https://archive.apache.org/dist/mahout/0.13.0/apache-mahout-distribution-0.13.0.tar.gz```
    ```tar -xzf apache-mahout-distribution-0.13.0.tar.gz```
    ```cp apache-mahout-distribution-0.13.0/lib/*.jar lib/```
    ```cp apache-mahout-distribution-0.13.0/*.jar lib/```

 3. Download hadoop client libs
    ```curl -O https://archive.apache.org/dist/hadoop/common/hadoop-2.8.5/hadoop-2.8.5.tar.gz```
    ```tar -xzf hadoop-2.8.5.tar.gz```
    ```cp hadoop-2.8.5/share/hadoop/common/*.jar lib/```
    ```cp hadoop-2.8.5/share/hadoop/common/lib/*.jar lib/```
 
 4. Set classpath

    ```export CLASSPATH=".:lib/*"```

 5. Compile the java files using following commands

    ```javac -cp ".;lib\mahout-mr-0.13.0.jar;lib\mahout-math-0.13.0.jar;lib\hadoop-common-2.8.5.jar;lib\commons-math3-3.6.1.jar;lib\slf4j-api-1.7.25.jar;lib\guava-20.0.jar" *.java```
    ```javac -cp $CLASSPATH *.java```
 
 6. Run the application 

    ```java -cp ".;lib\*" RecommendationDemo```

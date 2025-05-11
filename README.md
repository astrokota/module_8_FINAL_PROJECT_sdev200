1. Ensure JavaFX is listed in dependencies library in IntelliJ or whatever IDE is being used.
2. In IntelliJ, ensure VM options are correct (ie, add path of javafx to VM options)
     - Something like:
       --module-path "C:\insert_your_path_here\javafx-sdk-24.0.1\lib" --add-modules javafx.controls,javafx.fxml --enable-native-access=ALL-UNNAMED

(defproject webdev "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring "1.2.2"]
                 [compojure "1.1.6"]
                 [cheshire "5.3.1"]
                 [com.novemberain/validateur "2.0.0"]
                 [com.novemberain/monger "1.7.0"]]
  :min-lein-version "2.0.0"
  :uberjar-name "webdev.jar"
  :main webdev.core
  :profiles {:dev
             {:main webdev.core/-dev-main}})

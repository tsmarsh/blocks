(defproject blocks/server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.5.0"]
                 [com.outpace/config "0.13.4"]]

  :plugins [[lein-ring "0.12.5"]
            [lein-pprint "1.3.2"]]
  :ring {:handler blocks.handler/app}
  :profiles
  {:dev {:dependencies   [[javax.servlet/servlet-api "2.5"]
                          [ring/ring-mock "0.3.2"]]}})

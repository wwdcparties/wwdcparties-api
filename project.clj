(defproject wwdcparties "15.0.0"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [environ "1.0.0"]
                 [com.ashafa/clutch "0.4.0"]
                 [compojure "1.3.1"]
                 [ring/ring-core "1.3.2"]
                 [ring/ring-json "0.3.1"]
                 [crypto-password "0.1.3"]]

  :plugins [[lein-environ "1.0.0"]
            [lein-ring "0.8.13"]]

  :source-paths ["src"]
  :main ^:skip-aot wwdcparties.core
  :aot :all
  :uberjar-name "wwdcparties-api.jar"
  :ring {:handler wwdcparties.api/api :port 8080})

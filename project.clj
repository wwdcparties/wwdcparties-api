(defproject wwdcparties "15.0.0"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2665"]
                 [environ "1.0.0"]
                 [com.ashafa/clutch "0.4.0"]
                 [compojure "1.3.1"]
                 [ring/ring-core "1.3.2"]
                 [ring/ring-json "0.3.1"]]

  :node-dependencies [[source-map-support "0.2.8"]]

  :plugins [[lein-cljsbuild "1.0.4"]
            [lein-npm "0.4.0"]
            [lein-environ "1.0.0"]
            [lein-ring "0.8.13"]]

  :source-paths ["src/clj" "src/cljs" "target/classes"]
  :main ^:skip-aot wwdcparties.core
  :aot :all
  :uberjar-name "wwdcparties.jar"
  :ring {:handler wwdcparties.api/api :port 8080}

  :clean-targets ["out/wwdc_parties" "wwdc_parties.js" "wwdc_parties.min.js"]

  :cljsbuild {
    :builds [{:id "dev"
              :source-paths ["src"]
              :compiler {
                :output-to "wwdc_parties.js"
                :output-dir "out"
                :optimizations :none
                :cache-analysis true                
                :source-map true}}
             {:id "release"
              :source-paths ["src"]
              :compiler {
                :output-to "wwdc_parties.min.js"
                :pretty-print false              
                :optimizations :advanced}}]})

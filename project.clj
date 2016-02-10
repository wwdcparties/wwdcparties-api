(defproject wwdcparties "0.1.0-SNAPSHOT"

  :description "FIXME: write description"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [selmer "0.9.7"]
                 [markdown-clj "0.9.84"]
                 [environ "1.0.1"]
                 [ring-middleware-format "0.7.0"]
                 [metosin/ring-http-response "0.6.5"]
                 [bouncer "1.0.0"]
                 [org.clojure/tools.nrepl "0.2.12"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.slf4j/slf4j-log4j12 "1.7.13"]
                 [org.apache.logging.log4j/log4j-core "2.5"]
                 [com.taoensso/tower "3.0.2"]
                 [compojure "1.4.0"]
                 [ring-webjars "0.1.1"]
                 [ring/ring-defaults "0.1.5"]
                 [ring "1.4.0" :exclusions [ring/ring-jetty-adapter]]
                 [mount "0.1.7"]
                 [buddy "0.8.3"]
                 [metosin/compojure-api "0.24.3"]
                 [metosin/ring-swagger-ui "2.1.3-4"]
                 [org.webjars/webjars-locator-jboss-vfs "0.1.0"]
                 [org.immutant/web "2.1.1" :exclusions [ch.qos.logback/logback-classic]]
                 [inflections "0.9.13"]
                 [org.clojars.geoffpado/icalendar "0.0.2"]
                 [com.ashafa/clutch "0.4.0"]]

  :min-lein-version "2.0.0"
  :uberjar-name "wwdcparties.jar"
  :jvm-opts ["-server"]
  :resource-paths ["resources"]

  :main wwdcparties.core

  :plugins [[lein-environ "1.0.1"]
            [lein-sassy "1.0.7"]
            [lein-sassc "0.10.4"]]
  :sassc [{:src "resources/scss/main.scss"
           :style "nested"
           :output-to "resources/public/css/main.css"
           :import-path "resources/scss"}]
 
  :hooks [leiningen.sassc]
  :profiles
  {:uberjar 
   {:omit-source true
    :env {:production true}
    :aot :all
    :source-paths ["env/prod/clj"]}
   :dev [:project/dev :profiles/dev]
   :test [:project/test :profiles/test]
   :project/dev  {:dependencies [[prone "0.8.3"]
                                 [ring/ring-mock "0.3.0"]
                                 [ring/ring-devel "1.4.0"]
                                 [pjstadig/humane-test-output "0.7.1"]]
                  :source-paths ["env/dev/clj"]
                  :repl-options {:init-ns wwdcparties.core}
                  :injections [(require 'pjstadig.humane-test-output)
                               (pjstadig.humane-test-output/activate!)]
                  ;;when :nrepl-port is set the application starts the nREPL server on load
                  :env {:dev        true
                        :port       3000
                        :nrepl-port 7000}}
   :project/test {:env {:test       true
                        :port       3001
                        :nrepl-port 7001}}
   :profiles/dev {}
   :profiles/test {}})

(set-env! :dependencies
          '[[org.clojure/clojure "1.7.0"]
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
            [com.ashafa/clutch "0.4.0"]
            [mathias/boot-sassc "0.1.5"]
            [org.danielsz/system "0.3.0-SNAPSHOT"]
            [prone "0.8.3"]
            [ring/ring-mock "0.3.0"]
            [ring/ring-devel "1.4.0"]
            [pjstadig/humane-test-output "0.7.1"]]
          :source-paths #{"src/"}
          :resource-paths #{"resources/"})

(require '[mathias.boot-sassc :refer :all]
         '[system.boot :refer [system]]
         '[system.core :refer [defsystem]])

(task-options!
 pom  {:project 'wwdcparties
       :version "16.0.0"}
 jar  {:main 'wwdcparties.core}
 aot  {:all true}
 sass {:output-dir "public/css/"})

(defsystem dev-system [])

(deftask build 
  "Build the final WWDC Parties uberjar" []
  (set-env! :source-paths #(conj % "env/prod/clj"))
  (comp 
   (sass :output-style "compressed")
   (aot)
   (pom)
   (uber)
   (jar)
   (target)))

(deftask dev []
  (set-env! :source-paths #(conj % "env/dev/clj")
            :resource-paths #(conj % "dev-resources"))
  (comp
   (watch)
   (sass)
   (system :sys #'dev-system
           :auto true)
   (repl :server true
         :init-ns 'wwdcparties.core)))

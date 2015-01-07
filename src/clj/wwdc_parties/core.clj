(ns wwdc-parties.core
	(:require [environ.core :refer [env]]))

(defn -main []
	(println (str "Dev database is: " (env :wwdc-parties-db))))

(ns wwdcparties.config
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[wwdcparties started successfully]=-"))
   :middleware identity})

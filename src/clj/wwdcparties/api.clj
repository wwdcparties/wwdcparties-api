(ns wwdcparties.api
  (:require [wwdcparties.db :as db]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [ring.util.response :refer [resource-response response redirect]]))

(def continuation-map
  {:activitycontinuation
    {:apps ["287EDDET2B.com.cocoatype.wwdcparties"]}})

(defroutes api-routes
  (GET "/apple-app-site-association" []
    (response continuation-map))
  (GET "/parties" []
    (response (db/get-all-parties)))
  (POST "/parties" request
    (response (db/add-party (:body request))))
  (route/resources "/"))

(def api
  (-> api-routes
    (middleware/wrap-json-body)
    (middleware/wrap-json-response)))

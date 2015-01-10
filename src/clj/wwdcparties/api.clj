(ns wwdcparties.api
  (:require [wwdcparties.db :as db]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [ring.util.response :refer [resource-response file-response response redirect content-type]]))

(def continuation-map
  {:activitycontinuation
    {:apps ["287EDDET2B.com.cocoatype.wwdcparties"]}})

(defroutes api-routes
  (GET "/apple-app-site-association" []
    (-> (resource-response "apple-app-site-association.json" {:root "public"})
      (content-type "application/pkcs7-mime")))
  (context "/parties" []
    (GET "/" []
      (response (db/get-all-parties)))
    (POST "/" request
      (response (db/add-party (:body request))))
    (GET "/:slug" [slug]
      (response (db/get-party-by-slug slug))))
  (route/resources "/"))

(def api
  (-> api-routes
    (middleware/wrap-json-body)
    (middleware/wrap-json-response)))
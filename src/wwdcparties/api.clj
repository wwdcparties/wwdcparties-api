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
  (GET "/" []
    (redirect "/parties"))
  (context "/parties" []
    (GET "/" []
      (response (db/get-all-parties)))
    (POST "/" request
      (response (db/add-party (:body request))))
    (GET "/:slug" [slug]
      (response (db/get-party-by-slug slug))))
  (route/resources "/"))

(def cors-headers
  { "Access-Control-Allow-Origin" "*"
    "Access-Control-Allow-Headers" "Content-Type"
    "Access-Control-Allow-Methods" "GET,POST,OPTIONS"})

(defn all-cors [handler]
  (fn [request]
    (let [response (handler request)]
      (update-in response [:headers]
        merge cors-headers))))

(def api
  (-> (handler/site api-routes)
    (middleware/wrap-json-body)
    (middleware/wrap-json-response)
    (all-cors)))

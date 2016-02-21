(ns wwdcparties.api.core
  (:require [wwdcparties.api.db :as db]
            [wwdcparties.api.foursquare :as foursquare]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :refer [resource-response file-response response redirect content-type]]))

(def continuation-map
  {:activitycontinuation
    {:apps ["287EDDET2B.com.cocoatype.wwdcparties"]}})

(defroutes api-routes
  (context "/api" []
           (GET "/apple-app-site-association" []
                (-> (resource-response "apple-app-site-association.json" {:root "public"})
                    (content-type "application/pkcs7-mime")))
           (GET "/" []
                (redirect "/api/parties/"))
           (GET "/parties/" []
                (response (db/parties)))
           (GET "/parties/:slug/" [slug]
                (response (db/with-links (db/parties slug))))
           (POST "/submitted/" request
                 (response (db/add-party (:body request))))
           (GET "/suggestions" {params :params}
                (response (foursquare/suggestions (:q params)))))
  (route/resources "/"))

(def cors-headers
  {"Access-Control-Allow-Origin" "*"
   "Access-Control-Allow-Headers" "Content-Type"
   "Access-Control-Allow-Methods" "GET,POST,OPTIONS"})

(defn all-cors [handler]
  (fn [request]
    (let [response (handler request)]
      (update-in response [:headers]
        merge cors-headers))))

(def api handler/site)

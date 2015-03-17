(ns wwdcparties.api
  (:require [wwdcparties.db :as db]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])
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
       (redirect "/parties/"))
  (GET "/parties/" []
       (response (db/parties)))
  (POST "/parties/" request
       (response (db/add-party (:body request))))
  (GET "/parties/:slug/" [slug]
       (response (db/parties slug)))
  (GET "/parties/:slug/edits/" [slug]
       (response (db/edit-dates slug)))
  (POST "/parties/:slug/approved/" [slug]
        (friend/authenticated (response (db/approve-party slug))))
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

(def secure
  (friend/authenticate 
   api-routes
   {:unauthenticated-handler 
    (partial workflows/http-basic-deny "WWDC Parties")
    :workflows
    [(workflows/http-basic
      :credential-fn (partial creds/bcrypt-credential-fn (db/auth))
      :realm "/")]}))

(def api
  (-> (handler/site secure)
      (middleware/wrap-json-body {:keywords? true})
      (middleware/wrap-json-response)  
      (all-cors)))

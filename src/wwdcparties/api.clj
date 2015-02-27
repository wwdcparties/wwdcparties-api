(ns wwdcparties.api
  (:require [wwdcparties.db :as db]
            [wwdcparties.admin :as admin]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [ring.util.response :refer [resource-response response redirect 
                                        content-type status]]))

(def continuation-map
  {:activitycontinuation
    {:apps ["287EDDET2B.com.cocoatype.wwdcparties"]}})

(defn log-in [username password]
  (if (admin/validate username password)
    (response {:username username})
    (status (response {:error "incorrect password"}) 401)))

(defroutes api-routes
  (GET "/apple-app-site-association" []
    (-> (resource-response "apple-app-site-association.json" {:root "public"})
      (content-type "application/pkcs7-mime")))
  (GET "/" []
       (redirect "/parties/"))
  (POST "/login/" [username password]
        (log-in username password))
  (GET "/parties/" []
       (response (db/get-all-parties)))
  (POST "/parties/" request
        (response (db/add-party (:body request))))
  (GET "/parties/:slug/" [slug]
       (response (db/get-party-by-slug slug))) 
  (route/resources "/"))

(def api
  (-> (handler/site api-routes)
      (middleware/wrap-json-response)
      (middleware/wrap-json-params)))

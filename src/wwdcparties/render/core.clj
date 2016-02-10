(ns wwdcparties.render.core
  (:require [hiccup.form :refer :all]))

(def ^:dynamic *party* nil)

(def shared-head
  (list [:meta {:charset "utf-8"}]
        [:meta {:name "viewport"
                :content "initial-scale=1.0, width=device-width"}]
        [:link {:rel "stylesheet"
                :href "/css/main.css"}]
        [:link {:rel "stylesheet"
                :type "text/css"
                :href "//cloud.typography.com/6514774/622788/css/fonts.css"}]
        [:link {:rel "stylesheet"
                :type "text/css"
                :href "//cdn.jsdelivr.net/jquery.slick/1.5.5/slick.css"}]
        [:script {
               :src  "//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"}]))

(defn with-party* [f]
  (fn [& [maybe-party & rest :as args]]
    (if (and (thread-bound? #'*party*)
             (not (identical? maybe-party *party*)))
      (apply f *party* args)
      (apply f maybe-party rest))))

(defmacro defpope [name & body]
  `(do
     (defn ~name ~@body)
     (alter-var-root (var ~name) with-party*)))

(defmacro with-party [party & body]
  `(binding [*party* ~party] ~@body))

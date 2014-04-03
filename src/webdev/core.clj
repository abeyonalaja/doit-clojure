(ns webdev.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [compojure.core :refer [defroutes GET ANY POST PUT DELETE]]
            [compojure.route :refer [not-found]]
            [ring.handler.dump :refer [handle-dump]]))

(defn greet
  [req]
  {:status 200
   :body "Hello World!"
   :headers {}})

(defn goodbye
  [req]
  {:status 200
   :body "See ya"
   :headers {}})
(defn about
  [req]
  {:status 200
   :body "Hey there its Abey!"
   :headers {}})

(defn request
  [req]
  {:status 200
   :body (pr-str req)
   :headers {}})

(defn yo
  [req]
  (let [name (get-in req [:route-params :name])]
    {:status 200
     :body (str "Yo! " name "!")
     :headers {}}))

(def ops
  {"+" +
   "-" -
   "*" *
   ":" /})

(defn calc
  [req]
  (let [a (Integer. (get-in req [:route-params :a]))
        b (Integer. (get-in req [:route-params :b]))
        op (get-in req [:route-params :op])
        f (get ops op)]
    (if f
      {:status 200
       :body (str (f a b))
       :headers {}}
      {:status 400
       :body "wrong!"
       :headers {}})))

(defroutes routes
  (GET "/" [] greet)
  (GET "/goodbye" [] goodbye)
  (GET "/yo/:name" [] yo)
  (GET "/calc/:a/:op/:b" [] calc)

  (GET "/about" [] about)
  (GET "/request" [] handle-dump)
  (not-found "Page not found"))

(defn wrap-server
  [hdlr]
  (fn
    [req]
    (assoc-in (hdlr req) [:headers "Server"] "Listronica 9000")))

(def app
  (wrap-server
    (wrap-params routes)))
(defn -main
  [port]
  (jetty/run-jetty app {:port (Integer. port)}))

(defn -dev-main
  [port]
  (jetty/run-jetty (wrap-reload #'app) {:port (Integer. port)}))

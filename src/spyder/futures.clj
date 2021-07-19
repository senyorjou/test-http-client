(ns spyder.futures
  (:require [org.httpkit.client :as http]))

(defn handle-error [url status error]
  (println (format "Error getting %s" url))
  (println (format "Status returned: %d" status))
  (println (format "Error: %s" error)))


(defn process-response [url headers body]
  (println (format "Getting URL %s" url))
  (println (format "Last modified: %s" (:last-modified headers)))
  (println (format "Body is: %d bytes long" (count body)))
  url)

(defn callback-wiki [url]
  (http/get url {:keepalive 30000}
            (fn [{:keys [status headers body error]}]
              (if error
                (handle-error url status error)
                (process-response url headers body)))))

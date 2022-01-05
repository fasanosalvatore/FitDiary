package it.fitdiary.backend.gestioneutenza.security;

import org.springframework.security.web.header.HeaderWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HeaderWriteConfigure implements HeaderWriter {
    /**
     * metodo per settare gli headers per cors.
     * @param request request http.
     * @param response response http con header settato.
     */
    @Override
    public void writeHeaders(final HttpServletRequest request,
                             final HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods",
                "ACL, CANCELUPLOAD, CHECKIN, CHECKOUT, COPY, DELETE, GET, "
                        + "HEAD, LOCK, MKCALENDAR, MKCOL, MOVE, OPTIONS, POST, "
                        + "PROPFIND, PROPPATCH, PUT, REPORT, SEARCH, "
                        + "UNCHECKOUT, UNLOCK, UPDATE, VERSION-CONTROL");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept, Key, "
                        + "Authorization");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

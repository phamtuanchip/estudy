
package org.estudy.ui.portlet;

import static nl.captcha.Captcha.NAME;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceServingPortlet;

import nl.captcha.Captcha;
import nl.captcha.servlet.CaptchaServletUtil;

import org.exoplatform.webui.application.portlet.PortletApplicationController;
import org.gatein.common.logging.Logger;
import org.gatein.common.logging.LoggerFactory;
public class CaptchaController extends PortletApplicationController implements ResourceServingPortlet {

    /** . */
    private static final Logger log = LoggerFactory.getLogger(CaptchaController.class);

    private static final String PARAM_HEIGHT = "height";

    private static final String PARAM_WIDTH = "width";

    protected int _width = 200;

    protected int _height = 50;

    @Override
    public void init() throws PortletException {
        if (getInitParameter(PARAM_HEIGHT) != null) {
            _height = Integer.valueOf(getInitParameter(PARAM_HEIGHT));
        }

        if (getInitParameter(PARAM_WIDTH) != null) {
            _width = Integer.valueOf(getInitParameter(PARAM_WIDTH));
        }
    }

    public void serveResource(ResourceRequest req, ResourceResponse resp) throws PortletException, java.io.IOException {
        PortletSession session = req.getPortletSession();
        Captcha captcha;
        if (session.getAttribute(NAME) == null) {
            captcha = new Captcha.Builder(_width, _height).addText().gimp().addNoise().addBackground().build();

            session.setAttribute(NAME, captcha);
            writeImage(resp, captcha.getImage());

            return;
        }

        captcha = (Captcha) session.getAttribute(NAME);
        writeImage(resp, captcha.getImage());

    }

    public static void writeImage(ResourceResponse response, BufferedImage bi) {
        response.setProperty("Cache-Control", "private,no-cache,no-store");
        response.setContentType("image/png"); // PNGs allow for transparency. JPGs do not.
        try {
            CaptchaServletUtil.writeImage(response.getPortletOutputStream(), bi);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}

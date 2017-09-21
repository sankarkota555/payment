package com.payment.utils;

import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;

public class PaymentFonts {

  private PaymentFonts() {

  }

  public static final Font footerFont = new Font(FontFamily.HELVETICA, 7, Font.ITALIC);

  public static final Font boldFont = new Font(FontFamily.TIMES_ROMAN, 14, Font.BOLDITALIC);

  public static final Font textFont = new Font(FontFamily.COURIER, 14);

  public static final Font headingFont = new Font(FontFamily.HELVETICA, 17, Font.BOLDITALIC);
}

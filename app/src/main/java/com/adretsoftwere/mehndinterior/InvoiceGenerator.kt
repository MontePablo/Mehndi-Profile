package com.adretsoftwere.mehndinterior
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import com.adretsoftwere.mehndinterior.models.InvoiceData
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.Border
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.HorizontalAlignment
import com.itextpdf.layout.properties.TextAlignment
import java.io.File
import java.io.FileOutputStream

class InvoiceGenerator(data: InvoiceData) {
    lateinit var orderId:String
    lateinit var invoiceId:String
    lateinit var invoiceUri: Uri
    init {
        val officeAddress ="MEHNDI PROFILE INDUSTRIES PVT. LTD.\nSiv Sai Puram, Badangpet, hyderabad, Telangana, 500058\nPhone no.: 9246272658\nEmail: info@mehndipvc.com\nGSTIN: 19AAQCM4236P1ZM\nState: 19-West Bengal"
//        order.apply {
//            title = "L type white body kitchen"
//            lengths.addAll(arrayListOf("2.5", "4.1f", "3f"))
//            price = "32000"
//            totalPrice = "40000"
//            dateTime = "21 Jan 2023"
//            discountPercent = "8%"
//            address =
//                "Madarat, Baruipur\nSidheswari Kalitala\nnear Kalyani Construction Shop\npin-700144"
//            finalPriceAftrDiscnt = "35000"
//            finalPriceAfterTax = "42000"
//            addons.add(Addon().apply { name = "chimney";price = "2000";quantity = "2" })
//            addons.add(Addon().apply { name = "glass";price = "1000";quantity = "3" })
//            addons.add(Addon().apply { name = "rack";price = "4000";quantity = "1" })
//        }

//        val pdfpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//            .toString()
//        var file = File(pdfpath, "invoice.pdf")
//        invoiceUri=file.toUri()
//        var outputStream = FileOutputStream(file)
//        var pdfWriter = PdfWriter(outputStream)
//        var pdfDocument = PdfDocument(pdfWriter)
//        pdfDocument.defaultPageSize = PageSize.A4
//        var document = Document(pdfDocument)
//
////        val drawable=getDrawable(R.drawable.applogo_colouredbackround)
//        //        val bitmap=drawable!!.toBitmap()
////        val ims=applicationContext.resources.assets.open("image/applogo.png")
////        val bitmap=BitmapFactory.decodeStream(ims)
////        val stream=ByteArrayOutputStream()
////        CoroutineScope(Dispatchers.Default).launch {
////            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream)
////        }
////        val bitmapData=stream.toByteArray()
////        var imageData= ImageDataFactory.create(bitmapData)
////        val image= Image(imageData)
////        image.scaleToFit(50f,50f)
//
//        document.setTextAlignment(TextAlignment.RIGHT)
//        val headingTable = Table(floatArrayOf(300f, 300f))
////        headingTable.setBackgroundColor(DeviceRgb(13, 131, 221))
////            .setFontColor(DeviceRgb(255, 255, 255))
//        headingTable.addCell(
//            Cell().setBorder(Border.NO_BORDER)
//                .add(Paragraph(officeAddress).setTextAlignment(TextAlignment.LEFT)).setPadding(20f)
//        )
//       //applogo
//        document.add(Paragraph("Tax Invoice").setFontSize(15F))
//        val userInfoTable = Table(floatArrayOf(300f, 300f)).setHorizontalAlignment(HorizontalAlignment.CENTER)
//        userInfoTable.addCell(
//            Cell().setBorder(Border.NO_BORDER).add(Paragraph("Bill to:\n${data.user.name}\nContact no.: ${data.user.mobile}")).setFontSize(25F)
//            .setPadding(30f).setBold()
//        )
//        userInfoTable.addCell(
//            Cell().setBorder(Border.NO_BORDER).add(Paragraph("Order number:\n${data.order.order_id}\nDate.: ${data.order.date}")).setFontSize(25F)
//                .setPadding(30f).setBold()
//        )
//        document.add(Paragraph("invoice number: " + invoiceId).setTextAlignment(TextAlignment.RIGHT))
////        headingTable.addCell(image)
//        headingTable.addCell(
//            Cell().setBorder(Border.NO_BORDER).add(Paragraph("WoodPeaker")).setFontSize(25F)
//                .setPadding(30f)
//        )
//
//        document.add(headingTable)
//
//        userInfoTable.addCell(
//            Cell().setBorder(Border.NO_BORDER).add(Paragraph("Billed to"))
//                .setFontColor(DeviceRgb(158, 158, 158))
//        )
//        userInfoTable.addCell(
//            Cell().setBorder(Border.NO_BORDER).add(Paragraph("order id"))
//                .setFontColor(DeviceRgb(158, 158, 158))
//        )
//        userInfoTable.addCell(
//            Cell().setBorder(Border.NO_BORDER).add(Paragraph("Date of issue"))
//                .setFontColor(DeviceRgb(158, 158, 158))
//        )
//        userInfoTable.addCell(
//            Cell().setBorder(Border.NO_BORDER).add(Paragraph("Invoice Total"))
//                .setFontColor(DeviceRgb(158, 158, 158))
//        )
//        userInfoTable.addCell(
//            Cell().setBorder(Border.NO_BORDER)
//                .add(Paragraph())
//        )
//        userInfoTable.addCell(Cell().setBorder(Border.NO_BORDER).add(Paragraph(orderId)))
//        userInfoTable.addCell(Cell().setBorder(Border.NO_BORDER).add(Paragraph()))
//        userInfoTable.addCell(
//            Cell().setFontSize(15F).setFontColor(DeviceRgb(12, 131, 221))
//                .setBorder(Border.NO_BORDER)
//                .add(Paragraph("Rs ").setBold().setFontSize(25f))
//        )
//        document.add(userInfoTable)
//
//
//        document.add(
//            Paragraph("______________________________________________")
//                .setFontColor(DeviceRgb(13, 131, 221)).setFontSize(20F)
//        )
//
//        val itemHeading =
//            Table(floatArrayOf(200f, 200f, 100f, 100f)).setFontColor(DeviceRgb(13, 131, 221))
//        itemHeading.addCell(
//            Cell().setBorder(Border.NO_BORDER).add(Paragraph("Description"))
//                .setPadding(2f)
//        )
//        itemHeading.addCell(
//            Cell().setBorder(Border.NO_BORDER).add(Paragraph("Qnit Cost"))
//                .setPadding(2f)
//        )
//        itemHeading.addCell(
//            Cell().setBorder(Border.NO_BORDER).add(Paragraph("Quantity"))
//                .setPadding(2f)
//        )
//        itemHeading.addCell(
//            Cell().setBorder(Border.NO_BORDER).add(Paragraph("Amount"))
//                .setPadding(2f)
//        )
//        document.add(itemHeading)
//
////        document.add(Paragraph("Main unit").setPadding(10F).setTextAlignment(TextAlignment.LEFT))
//        val itemTable = Table(floatArrayOf(200f, 200f, 100f, 100f))
//        itemTable.addCell(
//            Cell().setBorder(Border.NO_BORDER).add(Paragraph())
//                .setPadding(2f)
//        )
//        itemTable.addCell(
//            Cell().setBorder(Border.NO_BORDER).add(Paragraph("Rs "))
//                .setPadding(2f)
//        )
//        itemTable.addCell(
//            Cell().setBorder(Border.NO_BORDER).add(Paragraph("1"))
//                .setPadding(2f)
//        )
//        itemTable.addCell(
//            Cell().setBorder(Border.NO_BORDER).add(Paragraph("Rs "))
//                .setPadding(2f)
//        )
//        document.add(itemTable)
//
//        for (a in order.addons) {
//            val itemTable = Table(floatArrayOf(200f, 200f, 100f, 100f))
//            itemTable.addCell(
//                Cell().setBorder(Border.NO_BORDER).add(Paragraph(a.name))
//                    .setPadding(2f)
//            )
//            itemTable.addCell(
//                Cell().setBorder(Border.NO_BORDER).add(Paragraph("Rs "+a.price))
//                    .setPadding(2f)
//            )
//            itemTable.addCell(
//                Cell().setBorder(Border.NO_BORDER).add(Paragraph(a.quantity))
//                    .setPadding(2f)
//            )
//            val p = (a.price.toInt() * a.quantity.toInt()).toString()
//            itemTable.addCell(
//                Cell().setBorder(Border.NO_BORDER).add(Paragraph("Rs "+p))
//                    .setPadding(2f)
//            )
//            document.add(itemTable)
//        }
//
//        document.add(
//            Paragraph("______________________________________________")
//                .setFontColor(DeviceRgb(13, 131, 221)).setFontSize(20F)
//        )
//
//
//        val finalPriceTable = Table(floatArrayOf(300f, 150f)).setBold()
//        finalPriceTable.setMarginLeft(350f)
//        finalPriceTable.addCell(
//            Cell().setBorder(Border.NO_BORDER).setFontColor(DeviceRgb(13, 131, 221))
//                .add(Paragraph("Total Price"))
//        )
//        finalPriceTable.addCell(Cell().setBorder(Border.NO_BORDER).add(Paragraph("Rs "+)))
//        finalPriceTable.addCell(
//            Cell().setBorder(Border.NO_BORDER).setFontColor(DeviceRgb(13, 131, 221))
//                .add(Paragraph("Discount"))
//        )
//        finalPriceTable.addCell(
//            Cell().setBorder(Border.NO_BORDER).add(Paragraph())
//        )
//        finalPriceTable.addCell(
//            Cell().setBorder(Border.NO_BORDER).setFontColor(DeviceRgb(13, 131, 221))
//                .add(Paragraph("Price After discount"))
//        )
//        finalPriceTable.addCell(
//            Cell().setBorder(Border.NO_BORDER).add(Paragraph("Rs "+))
//        )
//        finalPriceTable.addCell(
//            Cell().setBorder(Border.NO_BORDER).setFontColor(DeviceRgb(13, 131, 221))
//                .add(Paragraph("Price with GST(18%)"))
//        )
//        finalPriceTable.addCell(
//            Cell().setBorder(Border.NO_BORDER).add(Paragraph("Rs "))
//        )
//        document.add(finalPriceTable)
//
//
//
//        document.add(
//            Paragraph("Thank You!").setFontSize(15F).setBackgroundColor(DeviceRgb(13, 131, 221))
//                .setFontColor(DeviceRgb(255, 255, 255))
//        )
//        document.close()
    }
}
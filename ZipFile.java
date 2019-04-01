import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFile {
  public ZipFile() {
  
  }

    public ByteArrayOutputStream listBytesToZip(Map<String, byte[]> mapReporte, String extension) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        for (Entry<String, byte[]> reporte : mapReporte.entrySet()) {
            ZipEntry entry = new ZipEntry(reporte.getKey() + extension);
            entry.setSize(reporte.getValue().length);
            zos.putNextEntry(entry);
            zos.write(reporte.getValue());
        }
        zos.closeEntry();
        zos.close();
        return baos;
    }
	
    public byte[] comprimirParaMemoria(String arqEntrada) throws IOException {
        GZIPOutputStream gzos = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedInputStream bis = null;
        byte[] buffer = new byte[8 * 1024];
        try {
            int nBytes;
            bis = new BufferedInputStream(new FileInputStream(arqEntrada));
            gzos = new GZIPOutputStream(baos);
            while ((nBytes = bis.read(buffer)) > 0)
                gzos.write(buffer, 0, nBytes);
        } finally {
            gzos.close();
            bis.close();
        }
        return baos.toByteArray();
    }
}

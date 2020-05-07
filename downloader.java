import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

class DownLoad implements Runnable{

	String link;
	File out;

	public DownLoad(String link,File out){
		this.link=link;
		this.out=out;
	}

	public void run(){
		try{
			URL url=new URL(link);
			HttpURLConnection http=(HttpURLConnection)url.openConnection();
			double fileSize=(double)http.getContentLengthLong();
			BufferedInputStream in=new BufferedInputStream(http.getInputStream());
			FileOutputStream fos=new FileOutputStream(this.out);
			BufferedOutputStream bout=new BufferedOutputStream(fos,1024);
			byte[] Buffered=new byte[1024];
			double downloaded=0.00;
			int read=0;
			double percentDownloaded=0.00;

			while((read=in.read(Buffered,0,1024))>=0){
				bout.write(Buffered,0,read);
				downloaded+=read;
				percentDownloaded=(downloaded*100)/fileSize;
				//String percent=String.format("%.4f",percentDownloaded);
				System.out.println("\nDownloaded"+percentDownloaded+"% of file.");
				//System.out.printf("\nDownloaded 0.4"+percentDownloaded+"% of file.");
			}
			in.close();
			bout.close();
			System.out.println("Download complete");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}


public class downloader{
	public static void main(String [] arg){
		String link="http://www.rjspm.com/PDF/JavaTheCompleteReference.pdf";
		File out=new File("C:\\Users\\hp\\Desktop\\browser\\Java The Complete Reference.pdf");
		File out1=new File("C:\\Users\\hp\\Desktop\\browser\\folder\\Java The Complete Reference.pdf");

		new Thread(new DownLoad(link,out)).start();
		new Thread(new DownLoad(link,out1)).start();


	}
}


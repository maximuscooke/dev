package com.maximuscooke.lib.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.maximuscooke.lib.common.collections.CArrayList;

public abstract class CFile implements ISerializable, IFilePath
{
	private static final long serialVersionUID = 6415247277598789564L;

	private String mDirectory = null;
	private String mFileName = null;

	private static final int ZIP_BUFFER_SIZE = 4096;

	public CFile(String fName)
	{
		this(CFile.getWorkingDirectory(), fName);
	}

	public CFile(String dir, String fName)
	{
		mDirectory = dir;

		if (dir == null)
		{
			mDirectory = CFile.getWorkingDirectory();
		}

		if (!mDirectory.endsWith(getSeparator()))
		{
			mDirectory = mDirectory + getSeparator();
		}

		mFileName = fName;
	}

	public static String getSeparator()
	{
		return File.separator;
	}

	/**
	 * Removes the extension from a file name string.
	 * 
	 * @param str file name.
	 * @return filename, without extension.
	 */
	public static String removeExtension(String str)
	{
		if (str.contains("."))
		{
			return str.substring(0, str.lastIndexOf('.'));			
		}
		
		return str;
	}

	public static String getWorkingDirectory()
	{
		return System.getProperty("user.dir");
	}

	/**
	 * Returns the file extension from a file name string.
	 * 
	 * @param fileName name or path of file.
	 * @return extension type as string.
	 */
	public static String getFileExtension(String fileName)
	{
		int lastIndex = fileName.lastIndexOf(".");

		if (lastIndex > 0)
		{
			return fileName.substring(lastIndex + 1);
		}

		return null;
	}

	public static String getFileExtension(File file)
	{
		return getFileExtension(file.getName());
	}

	public static boolean exists(String directory, String fileName)
	{
		File f = new File(directory, fileName);

		return f.exists();
	}

	public static boolean exists(String filePath)
	{
		File f = new File(filePath);

		return f.exists();
	}

	/**
	 * Create a directory, if it does not exist
	 * 
	 * @param name directory name
	 * @return File object
	 */
	public static File createDirectory(String name)
	{
		return CFile.createDirectory(new File(name));
	}

	/**
	 * Create a number of directories
	 * 
	 * @param names of directories to create
	 */
	public static void createDirectories(String... names)
	{
		if (names != null && names.length > 0)
		{
			for (String name : names)
			{
				CFile.createDirectory(new File(name));
			}
		}
	}

	/**
	 * Create a directory, if it does not exist
	 * 
	 * @param dir  directory path
	 * @param name directory name
	 * @return File object
	 */
	public static File createDirectory(String dir, String name)
	{
		return createDirectory(new File(dir, name));
	}

	/**
	 * Create a directory, if it does not exist
	 * 
	 * @param dir directory name
	 * @return File object
	 */
	public static File createDirectory(File dir)
	{
		if (!dir.exists())
		{
			dir.mkdir();
		}

		return dir;
	}

	/**
	 * Delete file if it exist
	 * 
	 * @param directory to search
	 * @param fileName  name of file to delete
	 * @return true if file deleted
	 */
	public static boolean delete(String directory, String fileName)
	{
		return deleteIfExists(new File(directory, fileName));
	}

	public static boolean delete(String filePath)
	{
		return deleteIfExists(new File(filePath));
	}

	public static boolean deleteIfExists(String filePath)
	{
		return deleteIfExists(new File(filePath));
	}

	public static boolean deleteIfExists(String directory, String fileName)
	{
		return deleteIfExists(new File(directory, fileName));
	}

	public static boolean deleteIfExists(File f)
	{
		if (f.exists() == true)
		{
			return f.delete();
		}

		return false;
	}

	public static final boolean deleteDirectory(String path)
	{
		return deleteDirectory(new File(path));
	}

	public static final boolean deleteDirectory(File path)
	{
		if (path.exists())
		{
			File[] files = path.listFiles();

			for (int i = 0; i < files.length; i++)
			{
				if (files[i].isDirectory())
				{
					deleteDirectory(files[i]);
				} 
				else
				{
					files[i].delete();
				}
			}
		}

		return (path.delete());
	}

	public static final File[] getDirectories(String filePath)
	{
		File[] directories = new File(filePath).listFiles(File::isDirectory);

		return directories;
	}

	public static CArrayList<String> getListOfFilePaths(String directory)
	{
		return getListOfFilePaths(directory, null, false, false);
	}

	public static CArrayList<String> getListOfFilePaths(String directory, String extType)
	{
		return getListOfFilePaths(directory, extType, false, false);
	}

	public static CArrayList<String> getListOfFilePaths(String directory, boolean bNameOnly)
	{
		return getListOfFilePaths(directory, null, bNameOnly, false);
	}
	
	public static CArrayList<String> getListOfFilePaths(String directory, String extType, boolean bNameOnly)
	{
		return getListOfFilePaths(new File(directory), extType, bNameOnly, false);
	}

	public static CArrayList<String> getListOfFilePaths(String directory, String extType, boolean bNameOnly, boolean bStripExtension)
	{
		return getListOfFilePaths(new File(directory), extType, bNameOnly, bStripExtension);
	}

	/**
	 * Returns a collection of file paths or file names contained within the
	 * specified directory
	 * 
	 * @param directory directory to search
	 * @param extType   files of a specific type
	 * @param bNameOnly if true only return the name, else return full path
	 * @return collection of file names/paths
	 */
	public static CArrayList<String> getListOfFilePaths(File directory, String extType, boolean bNameOnly, boolean bStripExtension)
	{
		CArrayList<String> results = new CArrayList<String>();

		File[] files = directory.listFiles();

		if (files != null && files.length > 0)
		{
			for (File file : files)
			{
				boolean bAdd = (extType == null) ? true : file.getAbsolutePath().endsWith(extType);

				if (bAdd)
				{
					String fn = bNameOnly ? file.getName() : file.getAbsolutePath();
					
					results.add( !bStripExtension ? fn : removeExtension(fn) );
				}
			}
		}

		return results;
	}

	@Override
	public String getDirectory()
	{
		return mDirectory;
	}

	@Override
	public void setDirectory(String dir)
	{
		this.mDirectory = dir;
	}

	@Override
	public String getFileName()
	{
		return mFileName;
	}

	@Override
	public void setFileName(String fileName)
	{
		this.mFileName = fileName;
	}

	public static boolean canSave(String directory, String fileName)
	{
		return (directory != null && fileName != null && exists(directory));
	}

	public boolean canSave()
	{
		return CFile.canSave(this.getDirectory(), this.getFileName());
	}

	public final String getAbsolutePath()
	{
		File f = (this.getDirectory() == null) ? new File(this.getFileName()) : new File(this.getDirectory(), this.getFileName());

		return f.getAbsolutePath();
	}

	/**
	 * Create a zip file of all specified files
	 * 
	 * @param zipfile   zip file to create
	 * @param filenames all files to be added to zip file
	 * @throws Exception general i/o exception
	 */
	public static void zip(String zipfile, String... filenames) throws Exception
	{
		CFile.zip(zipfile, false, filenames);
	}

	public static void zip(String zipfile, boolean bLog, String... filenames) throws Exception
	{

		assert (zipfile != null) : "[CFile::zip] file name cannot be null";
		assert (filenames != null && filenames.length > 0) : "[CFile::zip] no input files speciified";

		File[] files = new File[filenames.length];

		for (int i = 0; i < filenames.length; i++)
		{
			files[i] = new File(filenames[i]);
		}

		zip(zipfile, bLog, files);
	}

	public static void zip(String zipfile, boolean bLog, File[] files) throws Exception
	{

		assert (zipfile != null) : "[CFile::zip] file name cannot be null";
		assert (files != null && files.length > 0) : "[CFile::zip] file list cannot be empty or null";

		if (bLog)
		{
			System.out.println("Creating zip file...<" + zipfile + ">");
		}

		byte[] buffer = new byte[CFile.ZIP_BUFFER_SIZE];

		FileOutputStream fout = new FileOutputStream(zipfile);

		ZipOutputStream zout = new ZipOutputStream(fout);

		for (File f : files)
		{

			if (bLog)
			{

				System.out.println("Adding file : " + f.getAbsolutePath());
			}

			FileInputStream fin = new FileInputStream(f);

			zout.putNextEntry(new ZipEntry(f.getName()));

			int length = 0;

			while ((length = fin.read(buffer)) > 0)
			{
				zout.write(buffer, 0, length);
			}

			zout.closeEntry();

			fin.close();

		}

		if (bLog)
		{
			System.out.println("Zip file created.");
		}

		zout.close();
	}

	/**
	 * Unzip a zip file
	 * 
	 * @param zipfile      file to unzip
	 * @param outputFolder folder to unzip files to
	 * @throws Exception
	 */
	public static void unZip(String zipfile, String outputFolder) throws Exception
	{
		CFile.unZip(zipfile, outputFolder, false);
	}

	public static void unZip(String zipfile, String outputFolder, boolean bLog) throws Exception
	{
		byte[] buffer = new byte[CFile.ZIP_BUFFER_SIZE];

		if (bLog)
		{
			System.out.println("Unzip file ...<" + zipfile + ">");
		}

		File folder = new File(outputFolder);

		CFile.createDirectory(folder);

		ZipInputStream zis = new ZipInputStream(new FileInputStream(zipfile));

		ZipEntry zentry;

		while ((zentry = zis.getNextEntry()) != null)
		{
			File f = new File(outputFolder + File.separator + zentry.getName());

			if (bLog)
			{

				System.out.println("Unzipping : " + f.getAbsolutePath());
			}

			new File(f.getParent()).mkdirs();

			FileOutputStream fos = new FileOutputStream(f);

			int length = 0;
			
			while ((length = zis.read(buffer)) > 0)
			{
				fos.write(buffer, 0, length);
			}

			fos.close();
		}

		zis.closeEntry();

		zis.close();

		if (bLog)
		{
			System.out.println("Unzip Completed.");
		}
	}

	@Override
	public String toString()
	{
		return "CFile [Directory=" + mDirectory + ", FileName=" + mFileName + "]";
	}
}

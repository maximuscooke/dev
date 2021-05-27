#include "CTextFile.h"
#include <fstream>
#include <string>
//#include <functional>
#include <random>
#include <chrono>

namespace  _COMMON_NS
{
    std::string CTextFile::getClassName() const 
    { 
        return std::string( (const char*)_QUOTE(CTextFile) );
    }

    void CTextFile::log() const
    {
        Count numLines = getLineCount();

        if (numLines > 0)
        {
            for(Index i = 0; i < numLines; i++)
            {
                std::cout << getLineAt(i) << std::endl;
            }
        }
        else
        {
            std::cout << "File is empty." << std::endl;
        }
    }

    void CTextFile::addLine(std::string ln)
    {
        this->mLines.push_back(ln);
    }

    void CTextFile::save(filePathPtr filePath, Bool bAppend)
    {
         _LOGF("saving file %s", filePath);

        int opt = std::ios::out;

        if (bAppend)
        {
            opt |= std::ios::app;
        }

        std::fstream f;

        f.open(filePath, opt);

        for(Index i = 0; i < getLineCount(); i++)
        {
            std::string ln = getLineAt(i);

            f << ln << std::endl;
        }

        if (f.is_open())
        {
            f.close();
        }
    }

    void CTextFile::swap(Index a, Index b)
    {
        std::string tmp = mLines[b];

        mLines[b] = mLines[a];

        mLines[a] = tmp;
    }

    void CTextFile::open(filePathPtr filePath)
    {
         _LOGF("opening file %s", filePath);

        std::ifstream f(filePath);

        if (f.is_open())
        {
            std::string ln;

            while ( getline (f,ln) )
            {
                this->addLine(ln);
            }
            f.close();
        }

        f.open(filePath, std::ios::in);
    }

    void CTextFile::sort(SortOrder order, std::function<Integer (std::string, std::string)> f)
    {
        _LOGF("sort vector");

        std::sort(mLines.begin(), mLines.end(), f);

        if (order == Ascending)
        {
            reverse();
        }
    }

    void CTextFile::shuffle()
    {
        _LOGF("shuffle vector");

        unsigned seed = std::chrono::system_clock::now().time_since_epoch().count();

        std::shuffle(std::begin(mLines), std::end(mLines), std::default_random_engine(seed));
    }
 
    std::vector<CLinePosition> CTextFile::findInFile(std::string target) const
    {
         std::vector<CLinePosition> results;

         for(Index idx = 0; idx < getLineCount(); idx++)
         {
             std::string ln = getLineAt(idx);

             std::size_t colpos = ln.find(target);

             if (colpos != -1L)
             {
                 CLinePosition lnpos = CLinePosition(idx, (Index)colpos);
                 
                 results.push_back( lnpos );
             }
         }

         return results;
    }
}
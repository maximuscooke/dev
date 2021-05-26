#ifndef _CTEXTFILE_H_
#define _CTEXTFILE_H_

#include <vector>
#include "CFile.h"
#include "CString.h"
#include "CLinePosition.h"

namespace  _COMMON_NS
{
    class CTextFile : public CFile
    {
        private:
            std::vector<std::string> mLines;

        public:
            _INLINE Count       getLineCount() const { return (Count)mLines.size(); }
            _INLINE std::string getFirst() const { return mLines.front(); };
            _INLINE std::string getLast() const { return mLines.back(); };
 
            _INLINE void        capacity(Index i) {  mLines.capacity(); }

            _INLINE void        clear() { mLines.clear(); }

            void                addLine(std::string ln);
            _INLINE void        addLine(stringPtr ln) { addLine(std::string( ln ) ); }
            _INLINE void        addLine(const CString& ln) { addLine( ln.getString() ); }
            
            _INLINE void        removeLineAt(Index i) {  mLines.erase(mLines.begin()+i); }
            _INLINE std::string getLineAt(Index i) const { return  mLines.at(i); }
            _INLINE void        setLineAt(Index i, std::string ln) { mLines[i] = ln; }
            _INLINE void        insertLineAt(std::string ln, Index i) { mLines.insert(mLines.begin()+i, ln); }

            _INLINE void        save(filePathPtr filePath) { save(filePath, false); }
            _INLINE void        reverse() { std::reverse(std::begin(mLines), std::end(mLines)); }
            void                save(filePathPtr filePath, Bool bAppend);
            void                open(filePathPtr filePath);
            void                swap(Index a, Index b);
            Bool                isEmpty() { return mLines.empty(); }
            void                sort(SortOrder order = Descending, std::function<Integer (std::string, std::string)> f = [](std::string a, std::string b) { return a > b; } );
            void                shuffle();


            std::vector<CLinePosition> findInFile(std::string) const;
            _INLINE std::vector<CLinePosition> findInFile(stringPtr target) const { return findInFile( std::string( target ) ); };
            _INLINE std::vector<CLinePosition> findInFile(CString& target) const { return findInFile( target.getString() ); };

            _OVERRIDE void     log() const;
  
            _OVERRIDE std::string getClassName() const { return std::string( (stringPtr)_QUOTE(CTextFile) ); }
    };
}
#endif
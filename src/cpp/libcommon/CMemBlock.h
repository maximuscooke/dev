#ifndef _CMEMBLOCK_H_
#define _CMEMBLOCK_H_

#include "CObject.h"


#define _MB_AUTO_DELETE (1<<0)
#define _MB_AUTO_INIT   (1<<1)

namespace  _COMMON_NS
{
    template <class T> 
    class CMemBlock : public CObject
    {
        private:
            T* mPtr=nullptr;
            Integer mOptions=0;
            Count mElementCount=0;
  
        public:
            CMemBlock(Count eleCount, Integer options = 0)
            {
                mOptions = options;

                alloc(eleCount);

                if (mOptions & _MB_AUTO_INIT)
                {
                    init();
                }
            }

            _GET_PROPERTY(Count, ElementCount, mElementCount)
            _GET_PROPERTY(T*, MemPtr, mPtr)
            _GET_PROPERTY(Integer, Options, mOptions)
            _SET_PROPERTY(Integer, Options, mOptions)

            T getAt(Index i) const 
            { 
                return mPtr[i]; 
            }
            
            void setAt(Index i, T ele) const 
            { 
                mPtr[i] = ele; 
            }

            ULong sizeInBytes() const 
            { 
                return (ULong)(sizeof(T) * mElementCount); 
            }

            void init(int val = 0) 
            {  
                if (mElementCount > 0 && mPtr != nullptr)
                {
                    std::memset(mPtr, val, sizeInBytes()); 
                }
            }

            void alloc(Count eleCount) 
            { 
                mPtr = new T[eleCount];

                mElementCount = eleCount; 
            }

            void free() 
            { 
                if (mPtr != nullptr) 
                { 
                    delete[] mPtr; 
                    
                    mPtr = nullptr; 
                } 
            }

            void resize(Count eleCount) 
            { 
                T* ptr = mPtr;

                Count count = mElementCount;

                alloc(eleCount); 

                for(Index i = 0; i < count; i++) 
                { 
                    mPtr[i] = ptr[i];
                }

                delete[] ptr;
            }

        public:
            _OVERRIDE ~CMemBlock() 
            { 
                if (mOptions & _MB_AUTO_DELETE)
                {
                     free();
                }
            }
    };
}
#endif

import React, { useCallback, useEffect, useState } from 'react'
import {FaAsterisk, FaQuestion, FaUser} from "react-icons/fa";
import { FaUserDoctor } from "react-icons/fa6";
import VitaminModal from "./VitaminModal";

const AskVitamins = () => {
    const [completeAsk, setCompleteAsk] = useState(true);
    const [openModal, setOpenModal] = useState(false);
    const [vitaminList, setVitaminList] = useState([]);

    const onOpenModal = () => {
        setOpenModal(!openModal);
    }

    return (
        <div>
            <div className='ms-4 mt-2 w-100 pt-2'>
                <FaQuestion className='text-3xl mb-3 text-red-600 inline me-2 drop-shadow-md'/>
                <span className='leading-3 text-3xl drop-shadow-md'>영양제를 물어봐요</span>
                <div className='ms-4 text-desc'>복용 중인 영양제, 증상, 특이사항을 입력하면 최적의 영양제 조합을 추천해드려요!</div>
            </div>
            <div className='grid grid-cols-12'>
                <div className='w-50 col-span-6 slider-inner-div user-section'>
                    <div className='mb-2.5'>
                        <FaUser className='inline mx-2 my-2'/>
                        <span className='mt-2'>정보를 입력해요</span>
                        <hr/>
                    </div>
                    <div className='w-100 pl-8 pr-8 pt-2'>
                        <form>
                            <div className="grid grid-cols-12 gap-2 mb-4">
                                <div className='pt-5 col-span-2'>
                                    <FaAsterisk className='inline px-1 mb-1 text-red-500'/>
                                    <label htmlFor="about" className="inline text-sm font-medium text-gray-900">증상</label>
                                </div>
                                <div className="inline mt-2 col-span-10">
                                    <textarea id="about" name="about" rows="2" className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></textarea>
                                </div>
                            </div>
                            <div className="grid grid-cols-12 gap-2 mb-4">
                                <div className='pt-5 col-span-2'>
                                    <FaAsterisk className='inline px-1 mb-1 text-red-500'/>
                                    <label htmlFor="about" className="inline text-sm font-medium text-gray-900">특이사항</label>
                                </div>
                                <div className="inline mt-2 col-span-10">
                                    <textarea id="about" name="about" rows="2" className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></textarea>
                                </div>
                            </div>

                            <div className="grid grid-cols-12 gap-2 mb-4">
                                <div className='pt-5 col-span-2'>
                                    <label htmlFor="about" className="inline ml-2 text-sm font-medium text-gray-900">복용 중인 영양제</label>
                                </div>
                                <div className="inline mt-2 col-span-10">
                                    <textarea id="about" name="about" rows="2" className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"></textarea>
                                </div>
                            </div>

                            <div className="flex items-center justify-end gap-x-6 mt-5 mb-5">
                                <button type="submit" className="btn-primary text-xs">질문하기</button>
                            </div>
                        </form>
                    </div>
                </div>
                <div className={`w-50 col-span-6 slider-inner-div ai-section ${completeAsk ? '' : 'opacity-10'}`}>
                    <div className='mb-2.5'>
                        <FaUserDoctor className='inline mx-2 my-2'/>
                        <span className='mt-2'>이러한 영양제를 추천해요</span>
                        <hr/>
                    </div>
                    <div className='w-100 pl-8 pr-8 pt-2'>
                        <div className="grid grid-cols-12 gap-2 mb-4">
                            <div className='col-span-6'>
                                <span className='pb-5 text-sm'>[종합의견]</span>
                                <div className='post-it-note'></div>
                            </div>
                            <div className="inline col-span-6">
                                <span  className='pb-5 text-sm'>[영양제 추천 이유]</span>
                                <div className='post-it-note'></div>
                            </div>
                        </div>

                        <div className="flex items-center justify-end gap-x-6 mt-8 mb-5">
                            <button type="button" className="btn-primary text-xs" onClick={onOpenModal}>영양제 상세보기</button>
                        </div>
                    </div>
                </div>
            </div>
            <>
                {openModal && (
                    <VitaminModal vitaminList={vitaminList} onOpenModal={onOpenModal}/>
                )}
            </>
        </div>
    );
};

export default AskVitamins;
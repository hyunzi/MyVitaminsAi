// store.js
import create from "zustand";

const useStore = create((set, get) => ({
    loading: true,
    defaultVitaminList: [
        {
            "name": "종합비타민",
            "effect": ["다양한 비타민과 미네랄을 공급하여 건강 유지에 도움"],
            "time": "아침 식사 후",
            "caution": ["과다 복용 시 오히려 부작용 발생 가능", "개인별 맞춤형 종합비타민 선택 권장"],
            "imgUrl" : ""
        },
        {
            "name": "유산균",
            "effect": ["장 건강 개선", "면역력 증진"],
            "time": "식사와 상관없이 언제든지",
            "caution": ["냉장 보관 필수", "장 기능 저하 시 복용 전 전문가 상담"],
            "imgUrl" : ""
        },
        {
            "name": "오메가-3",
            "effect": ["혈행 개선", "염증 감소"],
            "time": "식사와 함께",
            "caution": ["혈액 응고제 복용 시 주의", "어패류 알레르기 있을 경우 주의"],
            "imgUrl" : ""
        },
        {
            "name": "아연",
            "effect": ["면역력 증진", "상처 치유 촉진"],
            "time": "식사와 함께",
            "caution": ["구리 흡수를 방해할 수 있음", "과다 복용 시 구토, 설사 등 부작용 발생 가능"],
            "imgUrl" : ""
        },
        {
            "name": "마그네슘",
            "effect": ["근육 이완", "수면 개선"],
            "time": "저녁 식사 후",
            "caution": ["설사 유발 가능", "신장 질환 환자는 주의"],
            "imgUrl" : ""
        },
        {
            "name": "비타민 D",
            "effect": ["칼슘 흡수 촉진", "면역력 강화"],
            "time": "식사와 상관없이 언제든지",
            "caution": ["과다 복용 시 고칼슘혈증을 유발할 수 있음"],
            "imgUrl" : ""
        }
    ],
    setLoading: (loading) => set({ loading: loading }),
}));

export default useStore;